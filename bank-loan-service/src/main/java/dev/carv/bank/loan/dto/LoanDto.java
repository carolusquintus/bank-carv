package dev.carv.bank.loan.dto;

import dev.carv.bank.commons.annotation.ValueEnum;
import dev.carv.bank.loan.constant.LoanType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import static dev.carv.bank.commons.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

@Schema(
    name = "Loan",
    description = "Schema to hold Loan information")
public record LoanDto(

    @Schema(description = "Customer mobile number", example = "525512345678")
    @NotEmpty(message = "mobileNumber can not be null or empty")
    @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
    String mobileNumber,
    String loanNumber,

    @Schema(description = "Card type", example = "DEBIT")
    @NotNull(message = "type can not be null or empty")
    @ValueEnum(clazz = LoanType.class)
    String type,

    @NotNull(message = "totalLoan can not be null")
    @Positive(message = "totalLoan should be greater than zero")
    @Schema(description = "Total loan amount", example = "100000")
    BigDecimal totalLoan,

    @NotNull(message = "paidLoan can not be null")
    @PositiveOrZero(message = "paidLoan should be equal or greater than zero")
    @Schema(description = "Paid loan amount", example = "1000")
    BigDecimal paidLoan,

    @NotNull(message = "outstandingLoan can not be null")
    @PositiveOrZero(message = "outstaindingLoan should be equal or greater than zero")
    @Schema(description = "Outstanding amount against a loan", example = "90000")
    BigDecimal outstandingLoan

) {}
