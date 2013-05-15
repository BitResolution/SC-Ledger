package com.bitresolution.ledger;


import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

class Ledger {

    private static final Logger log = LoggerFactory.getLogger(Ledger.class);
    private static final EntryMarketValueComparator MARKET_VALUE_COMPARATOR = new EntryMarketValueComparator();

    private final Set<Report> reports;

    public Ledger() {
        reports = new HashSet<Report>();
    }

    private void loadReport(File reportFile) {
        try {
            ReportFileReader reader = new ReportFileReader(reportFile);
            Report report = reader.readReport();
            addReport(report);
        }
        catch (FileNotFoundException e) {
            log.error("Could not find report file: {}", reportFile);
        }
        catch (IOException e) {
            log.error("Failed to parse report file: {}", reportFile, e);
        }
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void loadReports(File... reportFiles) {
        for(File reportFile : reportFiles) {
            loadReport(reportFile);
        }
    }

    public int getNumberOfReports() {
        return reports.size();
    }

    Set<Report> getReports() {
        return ImmutableSet.copyOf(reports);
    }

    public List<Float> summariseStock(final String stockName) {
        log.debug("Summarising stock '{}' for period '{}'", stockName);
        ArrayList<Float> results = new ArrayList<Float>();
        for(Report report : reports) {
            log.debug("found report for period '{}'", report.getPeriodOfReport());
            Iterable<Entry> filteredEntry = Iterables.filter(report.getEntries(), new Predicate<Entry>() {
                @Override
                public boolean apply(@Nullable Entry entry) {
                    return entry.getTitleOfClass().contains(stockName);
                }
            });
            log.trace("found {} stocks from issuer '{}'", Iterables.size(filteredEntry), stockName);
            Iterable<Float> values = Iterables.transform(filteredEntry, new Function<Entry, Float>() {
                @Nullable
                @Override
                public Float apply(@Nullable Entry entry) {
                    return Float.parseFloat(entry.getMarketValue());
                }
            });
            float result = 0;
            for(Float value : values) {
                result += value;
            }
            log.debug("report {} stocks from issuer '{}' value is {}", new Object[]{report.getPeriodOfReport(), stockName, result});
            results.add(result);
        }
        log.debug("finished summarising stock {}: {}", stockName, results);
        return results;
    }

    public List<Entry> findLargestPositions(int topPerformerCount, DateTime cutoff) {
        BoundedSortedList<Entry> entries = new BoundedSortedList<Entry>(topPerformerCount, MARKET_VALUE_COMPARATOR);
        for(Report report : reports) {
            if(report.getPeriodOfReport().isBefore(cutoff)) {
                for(Entry entry : report.getEntries()) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    public List<Entry> findTopNewPerformers(int topNewPerformerCount, DateTime cuttoff) {
        ArrayList<Report> copy = Lists.newArrayList(reports);
        Collections.sort(copy, new Comparator<Report>() {
            @Override
            public int compare(Report report, Report report2) {
                return report.getPeriodOfReport().compareTo(report2.getPeriodOfReport());
            }
        });

        BoundedSortedList<Entry> entries = new BoundedSortedList<Entry>(topNewPerformerCount, MARKET_VALUE_COMPARATOR);
        Iterator<Report> iterator = reports.iterator();
        while(iterator.hasNext()) {
            Report report = iterator.next();
            if(report.getPeriodOfReport().isBefore(cuttoff)) {
                Report previous = iterator.next();
                for(Entry entry : report.getEntries()) {
                    if(previous != null && !previous.containsEntryWithNameOfIssuer(entry.getNameOfIssuer())) {
                        entries.add(entry);
                    }
                }
                break;
            }
        }
        return entries;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(reports);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Ledger other = (Ledger) obj;
        return Objects.equal(this.reports, other.reports);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("reports", reports)
                .toString();
    }
}
