package dev.carv.bank.account.dto;

import dev.carv.bank.account.constant.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(
    name = "Account",
    description = "Schema to hold Account information")
public record AccountDto(

    @Schema(description = "Customer account number", example = "1234567890")
    @NotEmpty(message = "accountNumber can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{1,10})", message = "accountNumber must be between 1 and 10 digits")
    Long accountNumber,

    @Schema(description = "Customer account type", example = "SAVINGS")
    @NotEmpty(message = "accountType can not be null or empty")
    AccountType type,

    @Schema(description = "Customer account branch address", example = "123 Main St, City, Country")
    @NotEmpty(message = "branchAddress can not be null or empty")
    String branchAddress

) {}
