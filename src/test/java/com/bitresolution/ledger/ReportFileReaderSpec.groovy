package com.bitresolution.ledger

import org.joda.time.DateTime
import spock.lang.Specification


class ReportFileReaderSpec extends Specification {

    def "should create report with correct PeriodOfReport"() {
        given:
        File source = new File(this.getClass().getResource("/single-entry-example.txt").toURI())
        ReportFileReader reader = new ReportFileReader(source)

        when:
        Report report = reader.readReport()

        then:
        assert report.periodOfReport == new DateTime("20120331")
    }
}
