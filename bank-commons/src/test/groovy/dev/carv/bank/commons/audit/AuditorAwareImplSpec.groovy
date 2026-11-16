package dev.carv.bank.commons.audit

import spock.lang.Specification

class AuditorAwareImplSpec extends Specification {

    def "auditor is the configured application name"() {
        given: "@Value injection is Spring's job, so we set the private field directly"
        def auditorAware = new AuditorAwareImpl()
        auditorAware.auditor = "bank-account-service"

        expect:
        auditorAware.currentAuditor == Optional.of("bank-account-service")
    }

}
