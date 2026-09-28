package dev.carv.bank.card.entity;

import dev.carv.bank.card.constant.CardType;
import dev.carv.bank.commons.annotation.GeneratedUUIDv7;
import dev.carv.bank.commons.entity.AuditEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

import static dev.carv.bank.card.constant.CardConstant.*;
import static jakarta.persistence.EnumType.STRING;

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

    @Enumerated(STRING)
    @Column(name = TYPE, nullable = false)
    private CardType type;

    @Column(name = LIMIT_AMOUNT, nullable = false)
    private BigDecimal limitAmount;

    @Column(name = USED_AMOUNT, nullable = false)
    private BigDecimal usedAmount;

    @Column(name = AVAILABLE_AMOUNT, nullable = false)
    private BigDecimal availableAmount;

}


