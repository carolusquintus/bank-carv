package dev.carv.bank.loan.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoanConstantAndTypeTest {

    @Test
    @DisplayName("LoanConstant should contain valid column and table name definitions")
    void testLoanConstant() {
        assertThat(LoanConstant.LOAN).isEqualTo("LOAN");
        assertThat(LoanConstant.ID).isEqualTo("ID");
        assertThat(LoanConstant.MOBILE_NUMBER).isEqualTo("MOBILE_NUMBER");
        assertThat(LoanConstant.LOAN_NUMBER).isEqualTo("LOAN_NUMBER");
        assertThat(LoanConstant.TYPE).isEqualTo("TYPE");
        assertThat(LoanConstant.TOTAL_LOAN).isEqualTo("TOTAL_LOAN");
        assertThat(LoanConstant.PAID_LOAN).isEqualTo("PAID_LOAN");
        assertThat(LoanConstant.OUTSTANDING_LOAN).isEqualTo("OUTSTANDING_LOAN");

        LoanConstant constantInstance = new LoanConstant();
        assertThat(constantInstance).isNotNull();
    }

    @Test
    @DisplayName("LoanType enum should contain expected values and valueOf should work")
    void testLoanType() {
        assertThat(LoanType.values()).containsExactly(
            LoanType.HOME,
            LoanType.CAR,
            LoanType.PERSONAL,
            LoanType.STUDENT
        );
        assertThat(LoanType.valueOf("HOME")).isEqualTo(LoanType.HOME);
        assertThat(LoanType.valueOf("CAR")).isEqualTo(LoanType.CAR);
        assertThat(LoanType.valueOf("PERSONAL")).isEqualTo(LoanType.PERSONAL);
        assertThat(LoanType.valueOf("STUDENT")).isEqualTo(LoanType.STUDENT);
    }
}
