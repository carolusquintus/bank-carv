package dev.carv.bank.card.dto

import spock.lang.Specification

class CardDtoSpec extends Specification {

    def "CardDto record should hold correct values and implement equals/hashCode/toString"() {
        given:
        def dto1 = new CardDto(
            "525512345678",
            "1234567890123456",
            "CREDIT",
            new BigDecimal("150000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("140000.00")
        )

        def dto2 = new CardDto(
            "525512345678",
            "1234567890123456",
            "CREDIT",
            new BigDecimal("150000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("140000.00")
        )

        def dto3 = new CardDto(
            "525500000000",
            "9999888877776666",
            "DEBIT",
            new BigDecimal("50000.00"),
            new BigDecimal("0.00"),
            new BigDecimal("50000.00")
        )

        expect:
        dto1.mobileNumber() == "525512345678"
        dto1.cardNumber() == "1234567890123456"
        dto1.type() == "CREDIT"
        dto1.limitAmount() == new BigDecimal("150000.00")
        dto1.usedAmount() == new BigDecimal("10000.00")
        dto1.availableAmount() == new BigDecimal("140000.00")

        dto1 == dto2
        dto1.hashCode() == dto2.hashCode()
        dto1 != dto3
        dto1.toString().contains("525512345678")
        dto1.toString().contains("1234567890123456")
        dto1.toString().contains("CREDIT")
    }
}
