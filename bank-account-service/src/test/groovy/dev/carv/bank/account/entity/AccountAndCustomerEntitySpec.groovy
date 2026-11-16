package dev.carv.bank.account.entity

import dev.carv.bank.account.constant.AccountType
import spock.lang.Specification

import java.util.UUID

class AccountAndCustomerEntitySpec extends Specification {

    def "AccountEntity should have working getters, setters, equals, hashCode, and toString"() {
        given:
        def customerId = UUID.randomUUID()
        def entity1 = new AccountEntity()
        entity1.customerId = customerId
        entity1.accountNumber = 1234567890L
        entity1.type = AccountType.SAVINGS
        entity1.branchAddress = "123 Main Street"

        def customer = new CustomerEntity()
        customer.id = customerId
        entity1.customer = customer

        def entity2 = new AccountEntity()
        entity2.customerId = UUID.randomUUID()

        expect:
        entity1.customerId == customerId
        entity1.accountNumber == 1234567890L
        entity1.type == AccountType.SAVINGS
        entity1.branchAddress == "123 Main Street"
        entity1.customer == customer

        entity1 == entity1
        entity1.hashCode() == entity1.hashCode()
        entity1 != entity2
        entity1 != null
        entity1 != new Object()
        entity1.canEqual(entity2) == true
        entity1.toString().contains("1234567890")
        entity1.toString().contains("SAVINGS")
        entity1.toString().contains("123 Main Street")
    }

    def "CustomerEntity should have working getters, setters, equals, hashCode, and toString"() {
        given:
        def id = UUID.randomUUID()
        def entity1 = new CustomerEntity()
        entity1.id = id
        entity1.name = "John Doe"
        entity1.email = "john@email.com"
        entity1.mobileNumber = "525512345678"

        def account = new AccountEntity()
        account.customerId = id
        entity1.account = account

        def entity2 = new CustomerEntity()
        entity2.id = UUID.randomUUID()

        expect:
        entity1.id == id
        entity1.name == "John Doe"
        entity1.email == "john@email.com"
        entity1.mobileNumber == "525512345678"
        entity1.account == account

        entity1 == entity1
        entity1.hashCode() == entity1.hashCode()
        entity1 != entity2
        entity1 != null
        entity1 != new Object()
        entity1.canEqual(entity2) == true
        entity1.toString().contains("John Doe")
        entity1.toString().contains("john@email.com")
        entity1.toString().contains("525512345678")
    }
}
