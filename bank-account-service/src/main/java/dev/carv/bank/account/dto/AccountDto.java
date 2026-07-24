package dev.carv.bank.account.dto;

import dev.carv.bank.account.constant.AccountType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AccountDto(

    @NotEmpty(message = "accountNumber can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{1,10})", message = "accountNumber must be between 1 and 10 digits")
    Long accountNumber,

    @NotEmpty(message = "accountType can not be null or empty")
    AccountType type,

    @NotEmpty(message = "branchAddress can not be null or empty")
    String branchAddress

) {}
