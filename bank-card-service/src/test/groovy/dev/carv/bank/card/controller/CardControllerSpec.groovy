package dev.carv.bank.card.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.carv.bank.card.constant.CardType
import dev.carv.bank.card.dto.CardDto
import dev.carv.bank.card.service.CardService
import dev.carv.bank.commons.constant.Message
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CardControllerSpec extends Specification {

    CardService service = Mock()
    CardController controller = new CardController(service)
    MockMvc mockMvc

    static final String MOBILE_NUMBER = "525512345678"
    static final String CARD_NUMBER = "1234567890123456"

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "createCard should return 201 Created with ResponseDto"() {
        given:
        1 * service.createCard(MOBILE_NUMBER)

        when:
        def response = controller.createCard(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.body.status() == HttpStatus.CREATED
        response.body.message() == Message.RESOURCE_CREATED.format("Card")
    }

    def "createCard MockMvc should return 201 Created"() {
        given:
        service.createCard(MOBILE_NUMBER) >> null

        expect:
        mockMvc.perform(post("/api/card")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isCreated())
            .andExpect(jsonPath('$.status').value("CREATED"))
            .andExpect(jsonPath('$.message').value(Message.RESOURCE_CREATED.format("Card")))
    }

    def "fetchCard should return 200 OK with CardDto"() {
        given:
        def dto = createCardDto()
        1 * service.fetchCard(MOBILE_NUMBER) >> dto

        when:
        def response = controller.fetchCard(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body == dto
    }

    def "fetchCard MockMvc should return 200 OK"() {
        given:
        def dto = createCardDto()
        service.fetchCard(MOBILE_NUMBER) >> dto

        expect:
        mockMvc.perform(get("/api/card")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.mobileNumber').value(MOBILE_NUMBER))
            .andExpect(jsonPath('$.cardNumber').value(CARD_NUMBER))
            .andExpect(jsonPath('$.type').value(CardType.CREDIT.name()))
    }

    def "updateCard should return 200 OK when update succeeds"() {
        given:
        def dto = createCardDto()
        1 * service.updateCard(dto) >> true

        when:
        def response = controller.updateCard(dto)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()
    }

    def "updateCard MockMvc should return 200 OK"() {
        given:
        def json = """{
          "mobileNumber": "${MOBILE_NUMBER}",
          "cardNumber": "${CARD_NUMBER}",
          "type": "CREDIT",
          "limitAmount": 150000.00,
          "usedAmount": 10000.00,
          "availableAmount": 140000.00
        }"""
        service.updateCard(_) >> true

        expect:
        mockMvc.perform(put("/api/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "updateCard should return 500 Internal Server Error when update fails"() {
        given:
        def dto = createCardDto()
        1 * service.updateCard(dto) >> false

        when:
        def response = controller.updateCard(dto)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
    }

    def "deleteCard should return 200 OK when delete succeeds"() {
        given:
        1 * service.deleteCard(MOBILE_NUMBER) >> true

        when:
        def response = controller.deleteCard(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()
    }

    def "deleteCard MockMvc should return 200 OK"() {
        given:
        service.deleteCard(MOBILE_NUMBER) >> true

        expect:
        mockMvc.perform(delete("/api/card")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "deleteCard should return 500 Internal Server Error when delete fails"() {
        given:
        1 * service.deleteCard(MOBILE_NUMBER) >> false

        when:
        def response = controller.deleteCard(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
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
