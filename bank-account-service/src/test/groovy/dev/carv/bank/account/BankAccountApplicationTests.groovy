package dev.carv.bank.account

import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Requires a running database - context load test skipped in unit test suite")
class BankAccountApplicationTests extends Specification {

    def "context loads"() {
        expect:
        true
    }
}
