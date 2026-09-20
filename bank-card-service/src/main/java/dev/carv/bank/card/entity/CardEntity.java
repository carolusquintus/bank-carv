package dev.carv.bank.card.entity;

import dev.carv.bank.commons.annotation.GeneratedUUIDv7;
import dev.carv.bank.commons.entity.AuditEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

import static dev.carv.bank.account.constant.AccountColumns.*;
import static dev.carv.bank.card.constant.CardConstant.*;
import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = CARD)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CardEntity extends AuditEntity {

    @Id
    @GeneratedUUIDv7
    @Column(name = ID, nullable = false)
    private UUID id;

    @Column(name = MOBILE_NUMBER, nullable = false)
    private String mobileNumber;

    @Column(name = CARD_NUMBER, nullable = false)
    private String cardNumber;

    @Column(name = MOBILE_NUMBER, nullable = false)
    private String mobileNumber;

    @OneToOne(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private AccountEntity account;

}


