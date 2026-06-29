package dev.carv.bank.account.dto;

import dev.carv.bank.account.constant.AccountType;

public record AccountDto(
    Long accountNumber,
    AccountType type,
    String branchAddress
) {}
