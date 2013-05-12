package com.bitresolution.ledger;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

public class Report {

    private final DateTime periodOfReport;

    public Report(DateTime periodOfReport) {
        this.periodOfReport = periodOfReport;
    }

    public DateTime getPeriodOfReport() {
        return periodOfReport;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(periodOfReport);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        return Objects.equal(this.periodOfReport, other.periodOfReport);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("periodOfReport", periodOfReport)
                .toString();
    }

    public static class Builder {
        private DateTime periodOfReport;


        public void setPeriodOfReport(DateTime periodOfReport) {
            this.periodOfReport = periodOfReport;
        }
        public Report build() {
            return new Report(periodOfReport);
        }
    }

}
