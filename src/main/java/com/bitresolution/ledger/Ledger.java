package com.bitresolution.ledger;


import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
            reports.add(report);
        }
        catch (FileNotFoundException e) {
            log.error("Could not find report file: {}", reportFile);
        }
        catch (IOException e) {
            log.error("Failed to parse report file: {}", reportFile, e);
        }
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

/**
 *
 */