package dev.carv.bank.card.service

import dev.carv.bank.card.constant.CardType
import dev.carv.bank.card.dto.CardDto
import dev.carv.bank.card.entity.CardEntity
import dev.carv.bank.card.mapper.CardMapper
import dev.carv.bank.card.repository.CardRepository
import dev.carv.bank.card.service.impl.CardServiceImpl
import dev.carv.bank.commons.exception.ResourceAlreadyExistsException
import dev.carv.bank.commons.exception.ResourceNotFoundException
import spock.lang.Specification

class CardServiceImplSpec extends Specification {

    CardMapper mapper = Mock()
    CardRepository repository = Mock()
    CardServiceImpl service = new CardServiceImpl(mapper, repository)

    static final String MOBILE_NUMBER = "525512345678"
    static final String CARD_NUMBER = "1234567890123456"

    def "createCard should save a new card when mobile number does not exist"() {
        given:
        repository.existsByMobileNumber(MOBILE_NUMBER) >> false
        CardEntity capturedCard = null

        when:
        service.createCard(MOBILE_NUMBER)

        then:
        1 * repository.save(_) >> { CardEntity it -> capturedCard = it }
        capturedCard != null
        capturedCard.getMobileNumber() == MOBILE_NUMBER
        capturedCard.getCardNumber().size() == 16
        capturedCard.getType() != null
        capturedCard.getLimitAmount() >= new BigDecimal("100000")
        capturedCard.getLimitAmount() <= new BigDecimal("200000")
        capturedCard.getUsedAmount().compareTo(BigDecimal.ZERO) == 0
        capturedCard.getAvailableAmount() == capturedCard.getLimitAmount()
    }

    def "createCard should throw ResourceAlreadyExistsException when mobile number already exists"() {
        given:
        repository.existsByMobileNumber(MOBILE_NUMBER) >> true

        when:
        service.createCard(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceAlreadyExistsException)
        ex.message.contains("Card")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * repository.save(_)
    }

    def "fetchCard should return CardDto when card is found"() {
        given:
        def entity = createCardEntity()
        def expectedDto = createCardDto()
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.of(entity)
        mapper.toDto(entity) >> expectedDto

        when:
        def actualDto = service.fetchCard(MOBILE_NUMBER)

        then:
        actualDto == expectedDto
    }

    def "fetchCard should throw ResourceNotFoundException when card is not found"() {
        given:
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.fetchCard(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Card")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * mapper.toDto(_)
    }

    def "updateCard should update entity fields and return true when card exists"() {
        given:
        def dto = createCardDto()
        def entity = createCardEntity()
        repository.findByCardNumber(dto.cardNumber()) >> Optional.of(entity)

        when:
        def updated = service.updateCard(dto)

        then:
        updated == true
        entity.getMobileNumber() == dto.mobileNumber()
        entity.getCardNumber() == dto.cardNumber()
        entity.getType() == CardType.valueOf(dto.type())
        entity.getLimitAmount() == dto.limitAmount()
        entity.getAvailableAmount() == dto.availableAmount()
        entity.getUsedAmount() == dto.usedAmount()
    }

    def "updateCard should throw ResourceNotFoundException when card does not exist"() {
        given:
        def dto = createCardDto()
        repository.findByCardNumber(dto.cardNumber()) >> Optional.empty()

        when:
        service.updateCard(dto)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Card")
        ex.message.contains("cardNumber")
        ex.message.contains(dto.cardNumber())
    }

    def "deleteCard should delete card by id and return true when card exists"() {
        given:
        def entity = createCardEntity()
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.of(entity)

        when:
        def deleted = service.deleteCard(MOBILE_NUMBER)

        then:
        deleted == true
        1 * repository.deleteById(entity.getId())
    }

    def "deleteCard should throw ResourceNotFoundException when card does not exist"() {
        given:
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.deleteCard(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Card")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * repository.deleteById(_)
    }

    private CardEntity createCardEntity() {
        def entity = new CardEntity()
        entity.setId(UUID.randomUUID())
        entity.setMobileNumber(MOBILE_NUMBER)
        entity.setCardNumber(CARD_NUMBER)
        entity.setType(CardType.CREDIT)
        entity.setLimitAmount(new BigDecimal("150000.00"))
        entity.setAvailableAmount(new BigDecimal("140000.00"))
        entity.setUsedAmount(new BigDecimal("10000.00"))
        return entity
    }

    private CardDto createCardDto() {
        new CardDto(
            MOBILE_NUMBER,
            CARD_NUMBER,
            CardType.CREDIT.name(),
            new BigDecimal("150000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("140000.00")
        )
    }
}
