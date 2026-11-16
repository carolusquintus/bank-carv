package dev.carv.bank.loan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class BankLoanApplicationTest {

    @Test
    @DisplayName("BankLoanApplication can be instantiated")
    void testInstantiation() {
        BankLoanApplication application = new BankLoanApplication();
        assertThat(application).isNotNull();
    }

    @Test
    @DisplayName("main method invokes SpringApplication.run")
    void testMainMethod() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(eq(BankLoanApplication.class), any(String[].class)))
                .thenReturn(null);

            BankLoanApplication.main(new String[]{});

            mockedSpringApplication.verify(() -> SpringApplication.run(eq(BankLoanApplication.class), any(String[].class)));
        }
    }
}
