package dev.carv.bank.loan.controller

import dev.carv.bank.commons.constant.Message
import dev.carv.bank.commons.dto.ResponseDto
import dev.carv.bank.loan.constant.LoanType
import dev.carv.bank.loan.dto.LoanDto
import dev.carv.bank.loan.service.LoanService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LoanControllerSpec extends Specification {

    LoanService service = Mock()
    LoanController controller = new LoanController(service)
    MockMvc mockMvc

    static final String MOBILE_NUMBER = "525512345678"
    static final String LOAN_NUMBER = "75255123456758"

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "createLoan should return 201 Created with ResponseDto"() {
        when:
        def response = controller.createLoan(MOBILE_NUMBER)

        then:
        1 * service.createLoan(MOBILE_NUMBER)
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.body.status() == HttpStatus.CREATED
        response.body.message() == Message.RESOURCE_CREATED.format("Loan")

        when: "the same request goes through the real MVC pipeline"
        def result = mockMvc.perform(post("/api/loan").param("mobileNumber", MOBILE_NUMBER))

        then:
        1 * service.createLoan(MOBILE_NUMBER)
        result.andExpect(status().isCreated())
            .andExpect(jsonPath('$.status').value("CREATED"))
            .andExpect(jsonPath('$.message').value(Message.RESOURCE_CREATED.format("Loan")))
    }

    def "fetchLoan should return 200 OK with LoanDto"() {
        given:
        def dto = createLoanDto()
        service.fetchLoan(MOBILE_NUMBER) >> dto

        when:
        def response = controller.fetchLoan(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body == dto

        and:
        mockMvc.perform(get("/api/loan").param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.mobileNumber').value(MOBILE_NUMBER))
            .andExpect(jsonPath('$.loanNumber').value(LOAN_NUMBER))
            .andExpect(jsonPath('$.type').value(LoanType.HOME.name()))
    }

    def "updateLoan should return 200 OK when update succeeds"() {
        given:
        def dto = createLoanDto()
        service.updateLoan(_ as LoanDto) >> true

        when:
        def response = controller.updateLoan(dto)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()

        and:
        def json = """
            {
              "mobileNumber": "${MOBILE_NUMBER}",
              "loanNumber": "${LOAN_NUMBER}",
              "type": "HOME",
              "totalLoan": 100000.00,
              "paidLoan": 10000.00,
              "outstandingLoan": 90000.00
            }
            """
        mockMvc.perform(put("/api/loan").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "updateLoan should return 500 Internal Server Error when update fails"() {
        given:
        def dto = createLoanDto()
        service.updateLoan(dto) >> false

        when:
        def response = controller.updateLoan(dto)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
    }

    def "deleteLoan should return 200 OK when delete succeeds"() {
        given:
        service.deleteLoan(MOBILE_NUMBER) >> true

        when:
        def response = controller.deleteLoan(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()

        and:
        mockMvc.perform(delete("/api/loan").param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "deleteLoan should return 500 Internal Server Error when delete fails"() {
        given:
        service.deleteLoan(MOBILE_NUMBER) >> false

        when:
        def response = controller.deleteLoan(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
    }

    private LoanDto createLoanDto() {
        new LoanDto(
            MOBILE_NUMBER,
            LOAN_NUMBER,
            LoanType.HOME.name(),
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        )
    }
}
