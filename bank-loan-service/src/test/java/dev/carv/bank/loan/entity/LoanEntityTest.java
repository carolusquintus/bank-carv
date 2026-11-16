package dev.carv.bank.loan.entity;

import dev.carv.bank.loan.constant.LoanType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoanEntityTest {

    @Test
    @DisplayName("LoanEntity should have working getters, setters, equals, hashCode, and toString")
    void testEntityMethods() {
        UUID id = UUID.randomUUID();
        LoanEntity entity1 = new LoanEntity();
        entity1.setId(id);
        entity1.setMobileNumber("525512345678");
        entity1.setLoanNumber("75255123456758");
        entity1.setType(LoanType.CAR);
        entity1.setTotalLoan(new BigDecimal("50000.00"));
        entity1.setPaidLoan(new BigDecimal("5000.00"));
        entity1.setOutstandingLoan(new BigDecimal("45000.00"));

        LoanEntity entity2 = new LoanEntity();
        entity2.setId(UUID.randomUUID());

        assertThat(entity1.getId()).isEqualTo(id);
        assertThat(entity1.getMobileNumber()).isEqualTo("525512345678");
        assertThat(entity1.getLoanNumber()).isEqualTo("75255123456758");
        assertThat(entity1.getType()).isEqualTo(LoanType.CAR);
        assertThat(entity1.getTotalLoan()).isEqualTo(new BigDecimal("50000.00"));
        assertThat(entity1.getPaidLoan()).isEqualTo(new BigDecimal("5000.00"));
        assertThat(entity1.getOutstandingLoan()).isEqualTo(new BigDecimal("45000.00"));

        // LoanEntity extends AuditEntity which doesn't implement equals, so callSuper=true uses Object.equals
        assertThat(entity1).isEqualTo(entity1);
        assertThat(entity1.hashCode()).isEqualTo(entity1.hashCode());
        assertThat(entity1).isNotEqualTo(entity2);
        assertThat(entity1).isNotEqualTo(null);
        assertThat(entity1).isNotEqualTo(new Object());
        assertThat(entity1.canEqual(entity2)).isTrue();
        assertThat(entity1.toString()).contains("525512345678", "75255123456758");
    }
}
