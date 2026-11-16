package dev.carv.bank.account

import org.mockito.MockedStatic
import org.springframework.boot.SpringApplication
import spock.lang.Specification

import static org.mockito.Mockito.mockStatic

class BankAccountApplicationSpec extends Specification {

    def "BankAccountApplication can be instantiated"() {
        expect:
        new BankAccountApplication() != null
    }

    def "main delegates to SpringApplication.run with the same arguments"() {
        given: "real arguments instead of Mockito matchers, which don't play well with Groovy overload resolution"
        def args = ["--spring.main.banner-mode=off"] as String[]
        MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication)

        when:
        BankAccountApplication.main(args)

        then:
        noExceptionThrown()
        springApplication.verify({ SpringApplication.run(BankAccountApplication, args) } as MockedStatic.Verification)

        cleanup: "static mocks are thread-local and must be closed"
        springApplication?.close()
    }

}
