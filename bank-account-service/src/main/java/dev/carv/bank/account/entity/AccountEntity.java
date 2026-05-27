package dev.carv.bank.account.entity;

import dev.carv.bank.account.constant.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "ACCOUNT")
@EqualsAndHashCode(callSuper = true)
public class AccountEntity extends AuditEntity {

    @Id
    @Column(name = "CUSTOMER_ID")
    private UUID customerId;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "TYPE")
    @Enumerated(STRING)
    private AccountType type;

    @Column(name = "BRANCH_ADDRESS")
    private String branchAddress;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerEntity customer;

}
