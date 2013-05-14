package com.bitresolution.ledger

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.joda.time.DateTime
import spock.lang.Specification


class LedgerSpec extends Specification {

    def "should be able to load report file"() {
        given:
        Ledger ledger = new Ledger()
        File source = new File(this.getClass().getResource("/single-entry-example.txt").toURI())

        when:
        ledger.loadReport(source)

        then:
        assert ledger.numberOfReports == 1
        assert ledger.reports == Sets.newHashSet(
                new Report(
                        new DateTime("20120331-01-01T00:00:00.000Z"),
                        new DateTime("20120515-01-01T00:00:00.000Z"),
                        Lists.newArrayList(
                                new Entry("AGILENT TECHNOLOGIES INC", "COM", "00846U101", "455", "10233", "SH", "SOLE", "", "10233", "0", "0")
                        )
                )
        )
    }

    def "should be able to load several report files at once"() {
        given:
        Ledger ledger = new Ledger()
        File sourceA = new File(this.getClass().getResource("/single-entry-example.txt").toURI())
        File sourceB = new File(this.getClass().getResource("/10-entry-example.txt").toURI())

        when:
        ledger.loadReports(sourceA, sourceB)

        then:
        assert ledger.numberOfReports == 2
        assert ledger.reports.contains(new Report(
                new DateTime("20120331"),
                new DateTime("20120515"),
                [new Entry("AGILENT TECHNOLOGIES INC", "COM", "00846U101", "455", "10233", "SH", "SOLE", "", "10233", "0", "0")]
        ))
        assert ledger.reports.contains(new Report(
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
        ))
    }

    def "should summarise COM stock portfolio"() {
        given:
        Ledger ledger = new Ledger()
        ledger.addReport(exampleDataSetA)

        when:
        def result = ledger.summariseStock("COM", new DateTime("20120331"))

        then:
        assert result == 4349.0
    }

    def "should list top 5 performers for a given date"() {
        given:
        Ledger ledger = new Ledger()
        ledger.addReport(exampleDataSetA)

        when:
        def result = ledger.findTopPerformersForDate(5, new DateTime("20120812"))

        then:
        assert result*.nameOfIssuer == [
                "ARCH CAP GROUP LTD",
                "ARCTIC CAT INC",
                "ALCOA INC",
                "AGILENT TECHNOLOGIES INC",
                "AARONS INC"
        ]
    }

    def "should list top 3 new positions for a given date"() {
        given:
        Ledger ledger = new Ledger()
        ledger.addReport(exampleDataSetA)
        ledger.addReport(exampleDataSetB)

        when:
        def result = ledger.findTopNewPerformersForDate(3, new DateTime("20120812"))

        then:
        assert result*.nameOfIssuer == [
                "ARCH CAP GROUP LTD",
                "ARCTIC CAT INC",
                "ALCOA INC",
                "AGILENT TECHNOLOGIES INC",
                "AARONS INC"
        ]
    }

    private static Report getExampleDataSetA() {
        return new Report(
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

    private static Report getExampleDataSetB() {
        return new Report(
                new DateTime("20120930-01-01T00:00:00.000Z"),
                new DateTime("20121109-01-01T00:00:00.000Z"),
                Lists.newArrayList(
                        new Entry("ABBOTT LABS", "COM", "002824100", "47", "13820", "SH", "SOLE", "", "13820", "0", "0"),
                        new Entry("ARCTIC CAT INC", "COM", "039670104", "1231", "5400", "SH", "SOLE", "", "5400", "0", "0"),
                        new Entry("ACE LTD", "SHS", "H0023R105", "959", "13499", "SH", "SOLE", "", "13099", "0", "0"),
                        new Entry("ARCH CAP GROUP LTD", "ORD", "G0450A105", "214", "5759", "SH", "SOLE", "", "5759", "0", "0")
                )
        )
    }
}


