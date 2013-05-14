package com.bitresolution.ledger;


import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
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

    public float summariseStock(final String stockName, final DateTime period) {
        log.debug("Summarising stock '{}' for period '{}'", stockName, period);
        for(Report report : reports) {
            if(report.getPeriodOfReport().equals(period)) {
                log.debug("found report for period '{}'", period);
                Iterable<Entry> filteredEntry = Iterables.filter(report.getEntries(), new Predicate<Entry>() {
                    @Override
                    public boolean apply(@Nullable Entry entry) {
                        return entry.getTitleOfClass().contains(stockName);
                    }
                });
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
                return result;
            }
        }

        return -1;
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

    public List findTopPerformersForDate(int topPerformerCount, DateTime cutoff) {
        BoundedSortedList<Entry> entries = new BoundedSortedList<Entry>(5, new Comparator<Entry>() {
            @Override
            public int compare(Entry entry, Entry entry2) {
                return entry.getMarketValue().compareTo(entry2.getMarketValue());
            }
        });
        for(Report report : reports) {
            if(report.getPeriodOfReport().isBefore(cutoff)) {
                for(Entry entry : report.getEntries()) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private static class BoundedSortedList<E> extends AbstractList<E> {

        private final List<E> backingList;
        private final Comparator<E> comparator;
        private final int maxSize;

        private BoundedSortedList(int maxSize, Comparator<E> comparator) {
            this.comparator = comparator;
            this.maxSize = maxSize;
            backingList = new ArrayList<E>();
        }

        @Override
        public E get(int i) {
            return backingList.get(i);
        }

        public boolean add(E e) {
            boolean added = backingList.add(e);
            Collections.sort(backingList, comparator);
            if(backingList.size() > maxSize) {
                backingList.remove(maxSize);
            }
            return added;
        }

        @Override
        public int size() {
            return backingList.size();
        }

        @Override
        public int hashCode() {
            return backingList.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return backingList.equals(obj);
        }
    }
}
