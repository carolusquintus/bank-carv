package dev.carv.bank.card.dto;

import dev.carv.bank.card.constant.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

import static dev.carv.bank.commons.constant.ValidationConstants.CARD_NUMBER_REGEX;
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
    @Pattern(regexp = CARD_NUMBER_REGEX, message = "cardNumber must be 12 digits")
    String cardNumber,

    @Schema(description = "Card type", example = "DEBIT")
    @NotEmpty(message = "type can not be null or empty")
    CardType type,

    @Positive(message = "limitAmount should be greater than zero")
    @Schema(description = "Limit amount available against a card", example = "100000")
    BigDecimal limitAmount,

    @PositiveOrZero(message = "usedAmount should be equal or greater than zero")
    @Schema(description = "Limit amount available against a card", example = "1000")
    BigDecimal usedAmount,

    @PositiveOrZero(message = "availableAmount should be equal or greater than zero")
    @Schema(description = "Amount available against a card", example = "90000")
    BigDecimal availableAmount

) {}
