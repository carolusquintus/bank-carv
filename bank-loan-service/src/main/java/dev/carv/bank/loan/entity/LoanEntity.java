package dev.carv.bank.loan.entity;

import dev.carv.bank.commons.annotation.GeneratedUUIDv7;
import dev.carv.bank.commons.entity.AuditEntity;
import dev.carv.bank.loan.constant.LoanType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

import static dev.carv.bank.loan.constant.LoanConstant.*;
import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = LOAN)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoanEntity extends AuditEntity {

    @Id
    @GeneratedUUIDv7
    @Column(name = ID, nullable = false)
    private UUID id;

    @Column(name = MOBILE_NUMBER, nullable = false)
    private String mobileNumber;

    @Column(name = LOAN_NUMBER, nullable = false)
    private String loanNumber;

    @Enumerated(STRING)
    @Column(name = TYPE, nullable = false)
    private LoanType type;

    @Column(name = TOTAL_LOAN, nullable = false)
    private BigDecimal totalLoan;

    @Column(name = PAID_LOAN, nullable = false)
    private BigDecimal paidLoan;

    @Column(name = OUTSTANDING_LOAN, nullable = false)
    private BigDecimal outstandingLoan;

}
