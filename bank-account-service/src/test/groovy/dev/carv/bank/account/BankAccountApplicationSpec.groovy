package dev.carv.bank.account

import org.mockito.MockedStatic
import org.springframework.boot.SpringApplication
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.mockStatic

class BankAccountApplicationSpec extends Specification {

    def "BankAccountApplication can be instantiated"() {
        when:
        def application = new BankAccountApplication()

        then:
        application != null
    }

    def "main method invokes SpringApplication.run"() {
        when:
        MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)
        try {
            mockedSpringApplication.when { SpringApplication.run(eq(BankAccountApplication.class), any(String[].class)) }
                .thenReturn(null)

            BankAccountApplication.main(new String[]{})

            mockedSpringApplication.verify { SpringApplication.run(eq(BankAccountApplication.class), any(String[].class)) }
        } finally {
            mockedSpringApplication.close()
        }

        then:
        noExceptionThrown()
    }
}
