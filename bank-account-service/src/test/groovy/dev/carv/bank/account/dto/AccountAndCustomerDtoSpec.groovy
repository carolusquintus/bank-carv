package dev.carv.bank.account.dto

import dev.carv.bank.account.constant.AccountType
import spock.lang.Specification

class AccountAndCustomerDtoSpec extends Specification {

    def "AccountDto should hold values and implement equals/hashCode/toString"() {
        given:
        def dto1 = new AccountDto(1234567890L, AccountType.SAVINGS, "123 Main St")
        def dto2 = new AccountDto(1234567890L, AccountType.SAVINGS, "123 Main St")
        def dto3 = new AccountDto(9999999999L, AccountType.SAVINGS, "456 Other St")

        expect:
        dto1.accountNumber() == 1234567890L
        dto1.type() == AccountType.SAVINGS
        dto1.branchAddress() == "123 Main St"

        dto1 == dto2
        dto1.hashCode() == dto2.hashCode()
        dto1 != dto3
        dto1.toString().contains("1234567890")
        dto1.toString().contains("SAVINGS")
        dto1.toString().contains("123 Main St")
    }

    def "CustomerDto should hold values and implement equals/hashCode/toString"() {
        given:
        def account = new AccountDto(1234567890L, AccountType.SAVINGS, "123 Main St")
        def customer1 = new CustomerDto("John Doe", "john@email.com", "525512345678", account)
        def customer2 = new CustomerDto("John Doe", "john@email.com", "525512345678", account)
        def customer3 = new CustomerDto("Jane Doe", "jane@email.com", "525599998888", null)

        expect:
        customer1.name() == "John Doe"
        customer1.email() == "john@email.com"
        customer1.mobileNumber() == "525512345678"
        customer1.account() == account

        customer1 == customer2
        customer1.hashCode() == customer2.hashCode()
        customer1 != customer3
        customer1.toString().contains("John Doe")
        customer1.toString().contains("john@email.com")
        customer1.toString().contains("525512345678")
    }
}
