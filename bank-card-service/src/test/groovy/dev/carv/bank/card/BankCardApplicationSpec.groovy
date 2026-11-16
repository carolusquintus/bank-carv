package dev.carv.bank.card

import org.mockito.MockedStatic
import org.springframework.boot.SpringApplication
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.mockStatic

class BankCardApplicationSpec extends Specification {

    def "BankCardApplication can be instantiated"() {
        when:
        def application = new BankCardApplication()

        then:
        application != null
    }

    def "main method invokes SpringApplication.run"() {
        when:
        MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)
        try {
            mockedSpringApplication.when { SpringApplication.run(eq(BankCardApplication.class), any(String[].class)) }
                .thenReturn(null)

            BankCardApplication.main(new String[]{})

            mockedSpringApplication.verify { SpringApplication.run(eq(BankCardApplication.class), any(String[].class)) }
        } finally {
            mockedSpringApplication.close()
        }

        then:
        noExceptionThrown()
    }
}
