package dev.carv.bank.card.mapper

import dev.carv.bank.card.constant.CardType
import dev.carv.bank.card.dto.CardDto
import dev.carv.bank.card.entity.CardEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CardMapperSpec extends Specification {

    private final CardMapper mapper = Mappers.getMapper(CardMapper.class)

    def "toDto should map CardEntity to CardDto correctly"() {
        given:
        def entity = new CardEntity()
        entity.setId(UUID.randomUUID())
        entity.setMobileNumber("525512345678")
        entity.setCardNumber("1234567890123456")
        entity.setType(CardType.DEBIT)
        entity.setLimitAmount(new BigDecimal("100000.00"))
        entity.setUsedAmount(new BigDecimal("5000.00"))
        entity.setAvailableAmount(new BigDecimal("95000.00"))

        when:
        def dto = mapper.toDto(entity)

        then:
        dto != null
        dto.mobileNumber() == entity.getMobileNumber()
        dto.cardNumber() == entity.getCardNumber()
        dto.type() == CardType.DEBIT.name()
        dto.limitAmount() == entity.getLimitAmount()
        dto.usedAmount() == entity.getUsedAmount()
        dto.availableAmount() == entity.getAvailableAmount()
    }

    def "toDto should return null when entity is null"() {
        expect:
        mapper.toDto(null) == null
    }

    def "toEntity should map CardDto to CardEntity correctly"() {
        given:
        def dto = new CardDto(
            "525512345678",
            "1234567890123456",
            "CREDIT",
            new BigDecimal("150000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("140000.00")
        )

        when:
        def entity = mapper.toEntity(dto)

        then:
        entity != null
        entity.getMobileNumber() == dto.mobileNumber()
        entity.getCardNumber() == dto.cardNumber()
        entity.getType() == CardType.CREDIT
        entity.getLimitAmount() == dto.limitAmount()
        entity.getUsedAmount() == dto.usedAmount()
        entity.getAvailableAmount() == dto.availableAmount()
    }

    def "toEntity should return null when DTO is null"() {
        expect:
        mapper.toEntity(null) == null
    }
}
