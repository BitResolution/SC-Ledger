package com.bitresolution.ledger;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

public class Report {

    private final DateTime periodOfReport;
    private final DateTime filingDate;

    public Report(DateTime periodOfReport, DateTime filingDate) {
        this.periodOfReport = periodOfReport;
        this.filingDate = filingDate;
    }

    public DateTime getPeriodOfReport() {
        return periodOfReport;
    }

    public DateTime getFilingDate() {
        return filingDate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(periodOfReport, filingDate);
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
        return Objects.equal(this.periodOfReport, other.periodOfReport) && Objects.equal(this.filingDate, other.filingDate);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("periodOfReport", periodOfReport)
                .add("filingDate", filingDate)
                .toString();
    }

    public static class Builder {
        private DateTime periodOfReport;
        private DateTime filingDate;

        public Builder setPeriodOfReport(DateTime periodOfReport) {
            this.periodOfReport = periodOfReport;
            return this;
        }

        public Builder setFilingDate(DateTime filingDate) {
            this.filingDate = filingDate;
            return this;
        }

        public Report build() {
            return new Report(periodOfReport, filingDate);
        }
    }

}
