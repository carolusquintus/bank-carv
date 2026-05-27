package dev.carv.bank.account.entity;

import dev.carv.bank.account.annotation.GeneratedUUIDv7;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "CUSTOMER")
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends AuditEntity {

    @Id
    @Column(name = "ID")
    @GeneratedUUIDv7
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @OneToOne(mappedBy = "customer", cascade = ALL)
    @PrimaryKeyJoinColumn
    private AccountEntity account;

}
