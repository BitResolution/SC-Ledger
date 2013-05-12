package com.bitresolution.ledger;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.CharBuffer;

public class ReportFileReader implements Readable {

    private static final Logger log = LoggerFactory.getLogger(ReportFileReader.class);

    private static final String PERIOD_OF_REPORT = "CONFORMED PERIOD OF REPORT:";

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
            if(line.startsWith(PERIOD_OF_REPORT)) {
                String value = line.substring(PERIOD_OF_REPORT.length()).trim();
                DateTime periodOfReport = new DateTime(value);
                report.setPeriodOfReport(periodOfReport);
                log.debug("Parsed 'period of report' {} from file '{}'", periodOfReport, source.getName());
            }
            else {
                log.trace("Skipping line: " + line);
            }
        }
        return report.build();
    }

}
