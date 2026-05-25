package dev.carv.bank.account.entity;

import dev.carv.bank.account.annotation.GeneratedUUIDv7;
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
public class AccountEntity extends AuditEntity {

    @Id
    @GeneratedUUIDv7
    private UUID id;

    @Column
    private UUID customerId;

    @Column
    private Integer number;

    @Column
    private String type;

    @Column
    private String branchAddress;

}
