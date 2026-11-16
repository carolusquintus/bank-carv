package dev.carv.bank.account.controller

import dev.carv.bank.account.constant.AccountType
import dev.carv.bank.account.dto.AccountDto
import dev.carv.bank.account.dto.CustomerDto
import dev.carv.bank.account.service.AccountService
import dev.carv.bank.commons.constant.Message
import dev.carv.bank.commons.dto.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountControllerSpec extends Specification {

    AccountService service = Mock()
    AccountController controller = new AccountController(service)
    MockMvc mockMvc

    static final String MOBILE_NUMBER = "525512345678"
    static final Long ACCOUNT_NUMBER = 1234567890L

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    private CustomerDto createCustomerDto() {
        new CustomerDto(
            "John Doe",
            "john@email.com",
            MOBILE_NUMBER,
            new AccountDto(ACCOUNT_NUMBER, AccountType.SAVINGS, "123 Main Street, New York")
        )
    }

    def "createAccount should return 201 Created with ResponseDto"() {
        given:
        def dto = createCustomerDto()

        when:
        ResponseEntity<ResponseDto> response = controller.createAccount(dto)

        then:
        1 * service.createAccount(dto)
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.body.status() == HttpStatus.CREATED
        response.body.message() == Message.RESOURCE_CREATED.format("Account")

        and: "MockMvc call also returns 201"
        def json = """
            {
              "name": "John Doe",
              "email": "john@email.com",
              "mobileNumber": "${MOBILE_NUMBER}"
            }
        """
        mockMvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath('$.status').value("CREATED"))
            .andExpect(jsonPath('$.message').value(Message.RESOURCE_CREATED.format("Account")))
    }

    def "fetchAccount should return 200 OK with CustomerDto"() {
        given:
        def dto = createCustomerDto()
        service.fetchAccount(MOBILE_NUMBER) >> dto

        when:
        ResponseEntity<CustomerDto> response = controller.fetchAccount(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body == dto

        and: "MockMvc call also returns 200"
        mockMvc.perform(get("/api/account")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.name').value("John Doe"))
            .andExpect(jsonPath('$.email').value("john@email.com"))
            .andExpect(jsonPath('$.mobileNumber').value(MOBILE_NUMBER))
    }

    def "updateAccount should return 200 OK when update succeeds"() {
        given:
        def dto = createCustomerDto()
        service.updateAccount(_) >> true

        when:
        ResponseEntity<ResponseDto> response = controller.updateAccount(dto)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()

        and: "MockMvc call also returns 200"
        def json = """
            {
              "name": "John Doe",
              "email": "john@email.com",
              "mobileNumber": "${MOBILE_NUMBER}",
              "account": {
                "accountNumber": ${ACCOUNT_NUMBER},
                "type": "SAVINGS",
                "branchAddress": "123 Main Street, New York"
              }
            }
        """
        mockMvc.perform(put("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "updateAccount should return 500 Internal Server Error when update fails"() {
        given:
        def dto = createCustomerDto()
        service.updateAccount(dto) >> false

        when:
        ResponseEntity<ResponseDto> response = controller.updateAccount(dto)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
    }

    def "deleteAccount should return 200 OK when delete succeeds"() {
        given:
        service.deleteAccount(MOBILE_NUMBER) >> true

        when:
        ResponseEntity<ResponseDto> response = controller.deleteAccount(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.OK
        response.body != null
        response.body.status() == HttpStatus.OK
        response.body.message() == Message.SUCCESS.getText()

        and: "MockMvc call also returns 200"
        mockMvc.perform(delete("/api/account")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.status').value("OK"))
            .andExpect(jsonPath('$.message').value(Message.SUCCESS.getText()))
    }

    def "deleteAccount should return 500 Internal Server Error when delete fails"() {
        given:
        service.deleteAccount(MOBILE_NUMBER) >> false

        when:
        ResponseEntity<ResponseDto> response = controller.deleteAccount(MOBILE_NUMBER)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.status() == HttpStatus.INTERNAL_SERVER_ERROR
        response.body.message() == Message.INTERNAL_ERROR.getText()
    }
}
