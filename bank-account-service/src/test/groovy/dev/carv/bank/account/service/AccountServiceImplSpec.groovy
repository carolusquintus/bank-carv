package dev.carv.bank.account.service

import dev.carv.bank.account.constant.AccountType
import dev.carv.bank.account.dto.AccountDto
import dev.carv.bank.account.dto.CustomerDto
import dev.carv.bank.account.entity.AccountEntity
import dev.carv.bank.account.entity.CustomerEntity
import dev.carv.bank.account.mapper.CustomerMapper
import dev.carv.bank.account.repository.AccountRepository
import dev.carv.bank.account.repository.CustomerRepository
import dev.carv.bank.account.service.impl.AccountServiceImpl
import dev.carv.bank.commons.exception.ResourceAlreadyExistsException
import dev.carv.bank.commons.exception.ResourceNotFoundException
import spock.lang.Specification

import java.util.Optional
import java.util.UUID

class AccountServiceImplSpec extends Specification {

    CustomerMapper customerMapper = Mock()
    AccountRepository accountRepository = Mock()
    CustomerRepository customerRepository = Mock()
    AccountServiceImpl service = new AccountServiceImpl(customerMapper, accountRepository, customerRepository)

    static final String MOBILE_NUMBER = "525512345678"
    static final Long ACCOUNT_NUMBER = 1234567890L

    private CustomerEntity createCustomerEntity() {
        def customer = new CustomerEntity()
        customer.id = UUID.randomUUID()
        customer.name = "John Doe"
        customer.email = "john@email.com"
        customer.mobileNumber = MOBILE_NUMBER
        customer
    }

    private AccountEntity createAccountEntity() {
        def account = new AccountEntity()
        account.customerId = UUID.randomUUID()
        account.accountNumber = ACCOUNT_NUMBER
        account.type = AccountType.SAVINGS
        account.branchAddress = "123 Main Street, New York"
        account
    }

    private CustomerDto createCustomerDto() {
        new CustomerDto(
            "John Doe",
            "john@email.com",
            MOBILE_NUMBER,
            new AccountDto(ACCOUNT_NUMBER, AccountType.SAVINGS, "123 Main Street, New York")
        )
    }

    def "createAccount should save new customer and account when customer does not exist"() {
        given:
        def dto = createCustomerDto()
        def customerEntity = createCustomerEntity()
        customerMapper.toEntity(dto) >> customerEntity
        customerRepository.existsByMobileNumber(MOBILE_NUMBER) >> false

        CustomerEntity savedCustomer = null

        when:
        service.createAccount(dto)

        then:
        1 * customerRepository.save({ CustomerEntity it ->
            savedCustomer = it
            true
        })
        savedCustomer != null
        savedCustomer.account != null
        savedCustomer.account.type == AccountType.SAVINGS
        savedCustomer.account.branchAddress == "123 Main Street, New York"
        savedCustomer.account.customer == savedCustomer
    }

    def "createAccount should throw ResourceAlreadyExistsException when mobile number already exists"() {
        given:
        def dto = createCustomerDto()
        def customerEntity = createCustomerEntity()
        customerMapper.toEntity(dto) >> customerEntity
        customerRepository.existsByMobileNumber(MOBILE_NUMBER) >> true

        when:
        service.createAccount(dto)

        then:
        def ex = thrown(ResourceAlreadyExistsException)
        ex.message.contains("Customer")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * customerRepository.save(_)
    }

    def "fetchAccount should return CustomerDto when customer is found"() {
        given:
        def customerEntity = createCustomerEntity()
        def expectedDto = createCustomerDto()
        customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER) >> Optional.of(customerEntity)
        customerMapper.toDto(customerEntity) >> expectedDto

        when:
        def actualDto = service.fetchAccount(MOBILE_NUMBER)

        then:
        actualDto == expectedDto
        1 * customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER)
        1 * customerMapper.toDto(customerEntity)
    }

    def "fetchAccount should throw ResourceNotFoundException when customer is not found"() {
        given:
        customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.fetchAccount(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Customer")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * customerMapper._
    }

    def "updateAccount should return false when AccountDto is null"() {
        given:
        def customerDto = new CustomerDto("John Doe", "john@email.com", MOBILE_NUMBER, null)

        when:
        def result = service.updateAccount(customerDto)

        then:
        result == false
        0 * accountRepository._
    }

    def "updateAccount should throw ResourceNotFoundException when account is not found"() {
        given:
        def customerDto = createCustomerDto()
        accountRepository.findByAccountNumber(ACCOUNT_NUMBER) >> Optional.empty()

        when:
        service.updateAccount(customerDto)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Account")
        ex.message.contains("accountNumber")
        ex.message.contains(ACCOUNT_NUMBER.toString())
    }

    def "updateAccount should update account and customer when customer is present"() {
        given:
        def customerDto = new CustomerDto("Jane Doe", "jane@email.com", "525599998888",
            new AccountDto(ACCOUNT_NUMBER, AccountType.SAVINGS, "456 New St, City"))
        def accountEntity = createAccountEntity()
        def customerEntity = createCustomerEntity()
        accountEntity.customer = customerEntity
        accountRepository.findByAccountNumber(ACCOUNT_NUMBER) >> Optional.of(accountEntity)

        when:
        def updated = service.updateAccount(customerDto)

        then:
        updated == true
        accountEntity.branchAddress == "456 New St, City"
        accountEntity.type == AccountType.SAVINGS
        customerEntity.name == "Jane Doe"
        customerEntity.email == "jane@email.com"
        customerEntity.mobileNumber == "525599998888"
    }

    def "updateAccount should update account when customer is null"() {
        given:
        def customerDto = new CustomerDto("Jane Doe", "jane@email.com", "525599998888",
            new AccountDto(ACCOUNT_NUMBER, AccountType.SAVINGS, "456 New St, City"))
        def accountEntity = createAccountEntity()
        accountEntity.customer = null
        accountRepository.findByAccountNumber(ACCOUNT_NUMBER) >> Optional.of(accountEntity)

        when:
        def updated = service.updateAccount(customerDto)

        then:
        updated == true
        accountEntity.branchAddress == "456 New St, City"
        accountEntity.type == AccountType.SAVINGS
    }

    def "deleteAccount should delete customer by id and return true when found"() {
        given:
        def customerEntity = createCustomerEntity()
        customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER) >> Optional.of(customerEntity)

        when:
        def deleted = service.deleteAccount(MOBILE_NUMBER)

        then:
        deleted == true
        1 * customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER)
        1 * customerRepository.deleteById(customerEntity.id)
    }

    def "deleteAccount should throw ResourceNotFoundException when customer not found"() {
        given:
        customerRepository.findByMobileNumberWithAccount(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.deleteAccount(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Customer")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * customerRepository.deleteById(_)
    }
}
