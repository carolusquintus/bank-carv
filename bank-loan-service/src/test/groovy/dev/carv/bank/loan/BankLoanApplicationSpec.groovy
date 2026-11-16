package dev.carv.bank.loan

import org.mockito.MockedStatic
import org.springframework.boot.SpringApplication
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.mockStatic

class BankLoanApplicationSpec extends Specification {

    def "BankLoanApplication can be instantiated"() {
        when:
        def application = new BankLoanApplication()

        then:
        application != null
    }

    def "main method invokes SpringApplication.run"() {
        when:
        MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)
        try {
            mockedSpringApplication.when(() -> SpringApplication.run(eq(BankLoanApplication.class), any(String[].class)))
                .thenReturn(null)

            BankLoanApplication.main(new String[]{})

            mockedSpringApplication.verify(() -> SpringApplication.run(eq(BankLoanApplication.class), any(String[].class)))
        } finally {
            mockedSpringApplication.close()
        }

        then:
        noExceptionThrown()
    }
}
