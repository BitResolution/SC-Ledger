package com.bitresolution.ledger

import com.google.common.collect.Lists
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

    def "should create report with correct FilingDate"() {
        given:
        File source = new File(this.getClass().getResource("/single-entry-example.txt").toURI())
        ReportFileReader reader = new ReportFileReader(source)

        when:
        Report report = reader.readReport()

        then:
        assert report.filingDate == new DateTime("20120515")
    }

    def "should create report with entry"() {
        given:
        File source = new File(this.getClass().getResource("/single-entry-example.txt").toURI())
        ReportFileReader reader = new ReportFileReader(source)

        when:
        Report report = reader.readReport()

        then:
        assert report.entries == [new Entry(
                "AGILENT TECHNOLOGIES INC",
                "COM",
                "00846U101",
                "455",
                "10233",
                "SH",
                "SOLE",
                "",
                "10233",
                "0",
                "0")]
    }

    def "should create report with multiple entries"() {
        given:
        File source = new File(this.getClass().getResource("/10-entry-example.txt").toURI())
        ReportFileReader reader = new ReportFileReader(source)

        when:
        Report report = reader.readReport()

        then:
        assert report == new Report(
                new DateTime("20120331-01-01T00:00:00.000Z"),
                new DateTime("20120515-01-01T00:00:00.000Z"),
                Lists.newArrayList(
                        new Entry("AGILENT TECHNOLOGIES INC", "COM", "00846U101", "455", "10233", "SH", "SOLE", "", "10233", "0", "0"),
                        new Entry("ALCOA INC", "COM", "013817101", "322", "32098", "SH", "SOLE", "", "32098", "0", "0"),
                        new Entry("AARONS INC", "COM PAR \$0.50", "002535300", "636", "24544", "SH", "SOLE", "", "24544", "0", "0"),
                        new Entry("ADVANCE AUTO PARTS INC", "COM", "00751Y106", "895", "10105", "SH", "SOLE", "", "10105", "0", "0"),
                        new Entry("APPLE INC", "COM", "037833100", "887", "1480", "SH", "SOLE", "", "1480", "0", "0"),
                        new Entry("ARBOR RLTY TR INC", "COM", "038923108", "76", "13600", "SH", "SOLE", "", "13600", "0", "0"),
                        new Entry("ABBOTT LABS", "COM", "002824100", "847", "13820", "SH", "SOLE", "", "13820", "0", "0"),
                        new Entry("ARCTIC CAT INC", "COM", "039670104", "231", "5400", "SH", "SOLE", "", "5400", "0", "0"),
                        new Entry("ACE LTD", "SHS", "H0023R105", "959", "13099", "SH", "SOLE", "", "13099", "0", "0"),
                        new Entry("ARCH CAP GROUP LTD", "ORD", "G0450A105", "214", "5759", "SH", "SOLE", "", "5759", "0", "0")
                )
        )
    }
}
