package dev.carv.bank.account.entity;

import dev.carv.bank.account.constant.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.util.UUID;

import static dev.carv.bank.account.constant.DBConstants.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.generator.EventType.INSERT;

@Data
@Entity
@Table(name = ACCOUNT)
@ToString(callSuper = true)
public class AccountEntity extends AuditEntity {

    @Id
    @Column(name = CUSTOMER_ID, nullable = false)
    private UUID customerId;

    @Column(name = NUMBER, insertable = false, updatable = false)
    @Generated(event = INSERT)
    private Long number;

    @Enumerated(STRING)
    @Column(name = TYPE, nullable = false)
    private AccountType type;

    @Column(name = BRANCH_ADDRESS, nullable = false)
    private String branchAddress;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = CUSTOMER_ID)
    private CustomerEntity customer;

}
