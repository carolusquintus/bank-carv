package dev.carv.bank.account.mapper

import dev.carv.bank.account.constant.AccountType
import dev.carv.bank.account.dto.AccountDto
import dev.carv.bank.account.dto.CustomerDto
import dev.carv.bank.account.entity.AccountEntity
import dev.carv.bank.account.entity.CustomerEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.UUID

class CustomerMapperSpec extends Specification {

    CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class)

    def "toDto should map CustomerEntity to CustomerDto correctly with account"() {
        given:
        def customer = new CustomerEntity()
        customer.id = UUID.randomUUID()
        customer.name = "John Doe"
        customer.email = "john@email.com"
        customer.mobileNumber = "525512345678"

        def account = new AccountEntity()
        account.customerId = customer.id
        account.accountNumber = 1234567890L
        account.type = AccountType.SAVINGS
        account.branchAddress = "123 Main Street, New York"
        customer.account = account

        when:
        CustomerDto dto = mapper.toDto(customer)

        then:
        dto != null
        dto.name() == "John Doe"
        dto.email() == "john@email.com"
        dto.mobileNumber() == "525512345678"
        dto.account() != null
        dto.account().accountNumber() == 1234567890L
        dto.account().type() == AccountType.SAVINGS
        dto.account().branchAddress() == "123 Main Street, New York"
    }

    def "toDto should map CustomerEntity to CustomerDto when account is null"() {
        given:
        def customer = new CustomerEntity()
        customer.id = UUID.randomUUID()
        customer.name = "John Doe"
        customer.email = "john@email.com"
        customer.mobileNumber = "525512345678"
        customer.account = null

        when:
        CustomerDto dto = mapper.toDto(customer)

        then:
        dto != null
        dto.name() == "John Doe"
        dto.email() == "john@email.com"
        dto.mobileNumber() == "525512345678"
        dto.account() == null
    }

    def "toDto should return null when customer entity is null"() {
        expect:
        mapper.toDto(null) == null
    }

    def "toEntity should map CustomerDto to CustomerEntity correctly with account"() {
        given:
        def dto = new CustomerDto(
            "John Doe",
            "john@email.com",
            "525512345678",
            new AccountDto(1234567890L, AccountType.SAVINGS, "123 Main Street, New York")
        )

        when:
        CustomerEntity entity = mapper.toEntity(dto)

        then:
        entity != null
        entity.name == "John Doe"
        entity.email == "john@email.com"
        entity.mobileNumber == "525512345678"
        entity.account != null
        entity.account.accountNumber == 1234567890L
        entity.account.type == AccountType.SAVINGS
        entity.account.branchAddress == "123 Main Street, New York"
    }

    def "toEntity should map CustomerDto to CustomerEntity when account is null"() {
        given:
        def dto = new CustomerDto("John Doe", "john@email.com", "525512345678", null)

        when:
        CustomerEntity entity = mapper.toEntity(dto)

        then:
        entity != null
        entity.name == "John Doe"
        entity.email == "john@email.com"
        entity.mobileNumber == "525512345678"
        entity.account == null
    }

    def "toEntity should return null when CustomerDto is null"() {
        expect:
        mapper.toEntity(null) == null
    }
}
