package dev.carv.bank.account.mapper

import dev.carv.bank.account.constant.AccountType
import dev.carv.bank.account.dto.AccountDto
import dev.carv.bank.account.entity.AccountEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.UUID

class AccountMapperSpec extends Specification {

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class)

    def "toDto should map AccountEntity to AccountDto correctly"() {
        given:
        def entity = new AccountEntity()
        entity.customerId = UUID.randomUUID()
        entity.accountNumber = 1234567890L
        entity.type = AccountType.SAVINGS
        entity.branchAddress = "123 Main Street, New York"

        when:
        AccountDto dto = mapper.toDto(entity)

        then:
        dto != null
        dto.accountNumber() == 1234567890L
        dto.type() == AccountType.SAVINGS
        dto.branchAddress() == "123 Main Street, New York"
    }

    def "toDto should return null when entity is null"() {
        expect:
        mapper.toDto(null) == null
    }

    def "toEntity should map AccountDto to AccountEntity correctly"() {
        given:
        def dto = new AccountDto(1234567890L, AccountType.SAVINGS, "123 Main Street, New York")

        when:
        AccountEntity entity = mapper.toEntity(dto)

        then:
        entity != null
        entity.accountNumber == 1234567890L
        entity.type == AccountType.SAVINGS
        entity.branchAddress == "123 Main Street, New York"
    }

    def "toEntity should return null when DTO is null"() {
        expect:
        mapper.toEntity(null) == null
    }
}
