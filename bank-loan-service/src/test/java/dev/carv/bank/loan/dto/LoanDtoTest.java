package dev.carv.bank.loan.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoanDtoTest {

    @Test
    @DisplayName("LoanDto record should hold correct values and implement equals/hashCode/toString")
    void testRecordMethods() {
        LoanDto dto1 = new LoanDto(
            "525512345678",
            "75255123456758",
            "HOME",
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        );

        LoanDto dto2 = new LoanDto(
            "525512345678",
            "75255123456758",
            "HOME",
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        );

        LoanDto dto3 = new LoanDto(
            "525500000000",
            "75255000000000",
            "PERSONAL",
            new BigDecimal("20000.00"),
            new BigDecimal("0.00"),
            new BigDecimal("20000.00")
        );

        assertThat(dto1.mobileNumber()).isEqualTo("525512345678");
        assertThat(dto1.loanNumber()).isEqualTo("75255123456758");
        assertThat(dto1.type()).isEqualTo("HOME");
        assertThat(dto1.totalLoan()).isEqualTo(new BigDecimal("100000.00"));
        assertThat(dto1.paidLoan()).isEqualTo(new BigDecimal("10000.00"));
        assertThat(dto1.outstandingLoan()).isEqualTo(new BigDecimal("90000.00"));

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.toString()).contains("525512345678", "75255123456758", "HOME");
    }
}
