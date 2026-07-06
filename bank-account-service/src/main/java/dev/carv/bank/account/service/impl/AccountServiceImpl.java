package dev.carv.bank.account.service.impl;

import dev.carv.bank.account.constant.ResponseMessage;
import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.entity.AccountEntity;
import dev.carv.bank.account.exception.CustomerAlreadyExistsException;
import dev.carv.bank.account.exception.ResourceNotFoundException;
import dev.carv.bank.account.mapper.CustomerMapper;
import dev.carv.bank.account.repository.CustomerRepository;
import dev.carv.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dev.carv.bank.account.constant.AccountType.SAVINGS;
import static dev.carv.bank.account.constant.ResponseMessage.CUSTOMER_ALREADY_EXISTS;
import static dev.carv.bank.account.constant.ResponseMessage.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

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

    private AccountEntity createAccount() {
        var newAccount = new AccountEntity();

        newAccount.setType(SAVINGS);
        newAccount.setBranchAddress("123 Main Street, New York");

        return newAccount;
    }

}
