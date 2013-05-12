package com.bitresolution.ledger;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.CharBuffer;

public class ReportFileReader implements Readable {

    private static final Logger log = LoggerFactory.getLogger(ReportFileReader.class);

    private static final String PERIOD_OF_REPORT = "CONFORMED PERIOD OF REPORT:";
    private static final String FILING_DATE = "FILED AS OF DATE:";
    private static final String COLUMN_HEADER = "NAME OF ISSUER";

    private final BufferedReader reader;
    private final File source;

    public ReportFileReader(File source) throws FileNotFoundException {
        this.source = source;
        reader = new LineNumberReader(new FileReader(source));
    }

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        return reader.read(charBuffer);
    }

    public Report readReport() throws IOException {
        Report.Builder report = new Report.Builder();
        String line;
        while((line = reader.readLine()) != null) {
            if(StringUtils.isBlank(line)
                    || line.startsWith(" ")
                    || line.startsWith(COLUMN_HEADER)
                    || line.startsWith("<S>")
                    || line.startsWith("</TABLE>")
                    || line.startsWith("</TEXT>")
                    || line.startsWith("</DOCUMENT>")
                    || line.startsWith("</SEC-DOCUMENT>")) {
                log.trace("Skipping line: " + line);
            }
            else if(line.startsWith(PERIOD_OF_REPORT)) {
                String value = line.substring(PERIOD_OF_REPORT.length()).trim();
                DateTime periodOfReport = new DateTime(value);
                report.setPeriodOfReport(periodOfReport);
                log.debug("Parsed 'period of report' {} from file '{}'", periodOfReport, source.getName());
            }
            else if(line.startsWith(FILING_DATE)) {
                String value = line.substring(FILING_DATE.length()).trim();
                DateTime filingDate = new DateTime(value);
                report.setFilingDate(filingDate);
                log.debug("Parsed 'filing date' {} from file '{}'", filingDate, source.getName());
            }
            else {
                try {
                    String[] values = line.split("[\\s]{2,}");
                    Entry.Builder entryBuilder = new Entry.Builder();
                    entryBuilder.setNameOfIssuer(values[0]);
                    entryBuilder.setTitleOfClass(values[1]);
                    entryBuilder.setCusip(values[2]);
                    entryBuilder.setMarketValue(values[3]);
                    entryBuilder.setAmount(values[4]);
                    entryBuilder.setCall(values[5]);
                    entryBuilder.setInvestmentDescretion(values[6]);
                    entryBuilder.setOtherManagers("");
                    entryBuilder.setVotingAuthoritySole(values[7]);
                    entryBuilder.setShared(values[8]);
                    entryBuilder.setNone(values[9]);
                    Entry entry = entryBuilder.build();

                    report.addEntry(entry);
                    log.debug("Parsed entry: {}", entry);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    log.error("Error parsing entry line: {}", line);
                }
            }
        }
        return report.build();
    }
}
