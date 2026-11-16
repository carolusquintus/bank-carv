package dev.carv.bank.card

import org.mockito.MockedStatic
import org.springframework.boot.SpringApplication
import spock.lang.Specification

import static org.mockito.Mockito.mockStatic

class BankCardApplicationSpec extends Specification {

    def "BankCardApplication can be instantiated"() {
        expect:
        new BankCardApplication() != null
    }

    def "main delegates to SpringApplication.run with the same arguments"() {
        given: "real arguments instead of Mockito matchers, which don't play well with Groovy overload resolution"
        def args = ["--spring.main.banner-mode=off"] as String[]
        MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication)

        when:
        BankCardApplication.main(args)

        then:
        noExceptionThrown()
        springApplication.verify({ SpringApplication.run(BankCardApplication, args) } as MockedStatic.Verification)

        cleanup: "static mocks are thread-local and must be closed"
        springApplication?.close()
    }

}
