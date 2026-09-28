package dev.carv.bank.card.dto;

import dev.carv.bank.card.constant.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

import static dev.carv.bank.commons.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

@Schema(
    name = "Card",
    description = "Schema to hold Card information")
public record CardDto (

    @Schema(description = "Customer mobile number", example = "525512345678")
    @NotEmpty(message = "mobileNumber can not be null or empty")
    @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
    String mobileNumber,

    @Schema(description = "Card number of the customer ", example = "525512345678")
    @NotEmpty(message = "cardNumber can not be null or empty")
    @Pattern(regexp = c, message = "mobileNumber must be 12 digits")
    String cardNumber,

    CardType type,

    BigDecimal limitAmount,

    BigDecimal usedAmount,

    BigDecimal availableAmount

) {}
