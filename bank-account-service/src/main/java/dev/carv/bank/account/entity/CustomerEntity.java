package dev.carv.bank.account.entity;

import dev.carv.bank.account.annotation.GeneratedUUIDv7;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

import static dev.carv.bank.account.constant.DBConstants.*;
import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = CUSTOMER)
@ToString(callSuper = true)
public class CustomerEntity extends AuditEntity {

    @Id
    @GeneratedUUIDv7
    @Column(name = ID, nullable = false)
    private UUID id;

    @Column(name = NAME, nullable = false)
    private String name;

    @Column(name = EMAIL, nullable = false)
    private String email;

    @Column(name = MOBILE_NUMBER, nullable = false)
    private String mobileNumber;

    @OneToOne(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    private AccountEntity account;

}


