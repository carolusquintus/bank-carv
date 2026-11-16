package dev.carv.bank.card.entity

import dev.carv.bank.card.constant.CardType
import spock.lang.Specification

class CardEntitySpec extends Specification {

    def "CardEntity should have working getters, setters, equals, hashCode, and toString"() {
        given:
        def id = UUID.randomUUID()
        def entity1 = new CardEntity()
        entity1.setId(id)
        entity1.setMobileNumber("525512345678")
        entity1.setCardNumber("1234567890123456")
        entity1.setType(CardType.CREDIT)
        entity1.setLimitAmount(new BigDecimal("150000.00"))
        entity1.setAvailableAmount(new BigDecimal("140000.00"))
        entity1.setUsedAmount(new BigDecimal("10000.00"))

        def entity2 = new CardEntity()
        entity2.setId(UUID.randomUUID())

        expect:
        entity1.getId() == id
        entity1.getMobileNumber() == "525512345678"
        entity1.getCardNumber() == "1234567890123456"
        entity1.getType() == CardType.CREDIT
        entity1.getLimitAmount() == new BigDecimal("150000.00")
        entity1.getAvailableAmount() == new BigDecimal("140000.00")
        entity1.getUsedAmount() == new BigDecimal("10000.00")

        entity1 == entity1
        entity1.hashCode() == entity1.hashCode()
        entity1 != entity2
        entity1 != null
        entity1 != new Object()
        entity1.canEqual(entity2) == true
        entity1.toString().contains("525512345678")
        entity1.toString().contains("1234567890123456")
        entity1.toString().contains("CREDIT")
    }
}
