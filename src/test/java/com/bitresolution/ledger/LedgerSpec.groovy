package com.bitresolution.ledger

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
        assert ledger.reports == Sets.newHashSet(new Report(
                new DateTime("20120331"),
                new DateTime("20120515"),
                [new Entry("AGILENT TECHNOLOGIES INC", "COM", "00846U101", "455", "10233", "SH", "SOLE", "", "10233", "0", "0")]
        ))
    }
}
