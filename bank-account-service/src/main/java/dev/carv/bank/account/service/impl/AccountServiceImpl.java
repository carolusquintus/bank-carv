package dev.carv.bank.account.service.impl;

import dev.carv.bank.account.constant.ResponseMessage;
import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.entity.AccountEntity;
import dev.carv.bank.account.entity.CustomerEntity;
import dev.carv.bank.account.exception.CustomerAlreadyExistsException;
import dev.carv.bank.account.exception.ResourceNotFoundException;
import dev.carv.bank.account.mapper.AccountMapper;
import dev.carv.bank.account.mapper.CustomerMapper;
import dev.carv.bank.account.repository.AccountRepository;
import dev.carv.bank.account.repository.CustomerRepository;
import dev.carv.bank.account.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.carv.bank.account.constant.AccountType.SAVINGS;
import static dev.carv.bank.account.constant.ResponseMessage.CUSTOMER_ALREADY_EXISTS;
import static dev.carv.bank.account.constant.ResponseMessage.RESOURCE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final CustomerMapper customerMapper;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto dto) {
        var customer = customerMapper.toEntity(dto);

        if (customerRepository.existsByMobileNumber(customer.getMobileNumber())) {
            throw new CustomerAlreadyExistsException(CUSTOMER_ALREADY_EXISTS.getMessage(), "mobileNumber", customer.getMobileNumber());
        }

        var account = createAccount();

        account.setCustomer(customer);
        customer.setAccount(account);

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        return customerRepository.findByMobileNumberWithAccount(mobileNumber)
            .map(customerMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND.getMessage(), "Customer", "mobileNumber", mobileNumber));
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        var accountDto = customerDto.account();
        if (accountDto != null) {
            var accountFound = accountRepository.findByAccountNumber(accountDto.accountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND.getMessage(), "Account", "accountNumber", accountDto.accountNumber().toString()));

            accountFound.setBranchAddress(accountDto.branchAddress());
            accountFound.setType(accountDto.type());

            var customerFound = accountFound.getCustomer();
            if (customerFound != null) {
                customerFound.setName(customerDto.name());
                customerFound.setEmail(customerDto.email());
                customerFound.setMobileNumber(customerDto.mobileNumber());
            }
            return true;
        }
        return false;
    }

    private AccountEntity createAccount() {
        var newAccount = new AccountEntity();

        newAccount.setType(SAVINGS);
        newAccount.setBranchAddress("123 Main Street, New York");

        return newAccount;
    }

}
