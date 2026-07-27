package dev.carv.bank.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static dev.carv.bank.account.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

public record CustomerDto(

    @NotEmpty(message = "name can not be null or empty")
    @Size(min = 5, max = 100, message = "The length of the customer name should be between 5 and 100")
    String name,

    @NotEmpty(message = "email can not be null or empty")
    @Email(message = "Email address should be a valid value")
    String email,

    @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
    String mobileNumber,

    AccountDto account

) {}
