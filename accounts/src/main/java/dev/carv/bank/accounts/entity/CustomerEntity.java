package dev.carv.bank.accounts.entity;

import dev.carv.bank.accounts.annotation.GeneratedUUIDv7;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "CUSTOMER")
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends AuditEntity {

    @Id
    @GeneratedUUIDv7
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String mobileNumber;

}
