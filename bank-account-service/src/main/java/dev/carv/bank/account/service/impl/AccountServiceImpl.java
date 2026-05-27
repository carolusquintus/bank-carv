package dev.carv.bank.account.service.impl;

import dev.carv.bank.account.constant.AccountType;
import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.entity.AccountEntity;
import dev.carv.bank.account.entity.CustomerEntity;
import dev.carv.bank.account.mapper.CustomerMapper;
import dev.carv.bank.account.repository.AccountRepository;
import dev.carv.bank.account.repository.CustomerRepository;
import dev.carv.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static dev.carv.bank.account.constant.AccountType.SAVINGS;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void createAccount(CustomerDto dto) {
        var customer = customerMapper.toEntity(dto);

        var savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    private AccountEntity createAccount(CustomerEntity customer) {
        var newAccount = new AccountEntity();
        long randomAccountNumber = 1_000_000_000L + new SecureRandom().nextInt(900_000_000);

        newAccount.setCustomerId(customer.getId());
        newAccount.setNumber(randomAccountNumber);
        newAccount.setType(SAVINGS);
        newAccount.setBranchAddress("123 Main Street, New York");

        return newAccount;
    }

}
