package dev.carv.bank.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDto(

    @NotEmpty(message = "name can not be null or empty")
    @Size(min = 5, max = 100, message = "The length of the customer name should be between 5 and 100")
    String name,

    @NotEmpty(message = "email can not be null or empty")
    @Email(message = "Email address should be a valid value")
    String email,

    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
    String mobileNumber,

    AccountDto account

) {}
