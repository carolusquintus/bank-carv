package dev.carv.bank.account.service;

import dev.carv.bank.account.dto.CustomerDto;

public interface AccountService {

    void createAccount(CustomerDto dto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto dto);

}
