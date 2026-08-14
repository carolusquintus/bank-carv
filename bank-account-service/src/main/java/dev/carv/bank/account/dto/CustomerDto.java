package dev.carv.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static dev.carv.bank.account.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

@Schema(
    name = "Customer",
    description = "Schema to hold Customer and Account information")
public record CustomerDto(

    @Schema(description = "Customer name", example = "John Doe")
    @NotEmpty(message = "name can not be null or empty")
    @Size(min = 5, max = 100, message = "The length of the customer name should be between 5 and 100")
    String name,

    @Schema(description = "Customer email", example = "john.doe@email.com")
    @NotEmpty(message = "email can not be null or empty")
    @Email(message = "Email address should be a valid value")
    String email,

    @Schema(description = "Customer mobile number", example = "525512345678")
    @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
    String mobileNumber,

    @Schema(description = "Customer account information details")
    AccountDto account

) {}
