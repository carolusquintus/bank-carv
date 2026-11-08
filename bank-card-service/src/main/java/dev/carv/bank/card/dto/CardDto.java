package dev.carv.bank.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.carv.bank.card.constant.CardType;
import dev.carv.bank.commons.annotation.ValueEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NUMBER;
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

    @Schema(description = "Card number of the customer ", example = "1234567890123456")
    @NotEmpty(message = "cardNumber can not be null or empty")
    @Pattern(regexp = CARD_NUMBER_REGEX, message = "cardNumber must be 16 digits")
    String cardNumber,

    @Schema(description = "Card type", example = "DEBIT")
    @NotNull(message = "type can not be null or empty")
    @ValueEnum(clazz = CardType.class)
    String type,

    @Digits(integer = 8, fraction = 2, message = "limitAmount must have at most 8 digits and at most 2 decimal places")
    @Positive(message = "limitAmount should be greater than zero")
    @Schema(description = "Limit amount available against a card", example = "100000")
    @JsonFormat(shape = NUMBER, pattern = "0.00")
    BigDecimal limitAmount,

    @Digits(integer = 8, fraction = 2, message = "usedAmount must have at most 8 digits and at most 2 decimal places")
    @PositiveOrZero(message = "usedAmount should be equal or greater than zero")
    @Schema(description = "Limit amount available against a card", example = "1000")
    @JsonFormat(shape = NUMBER, pattern = "0.00")
    BigDecimal usedAmount,

    @Digits(integer = 8, fraction = 2, message = "availableAmount must have at most 8 digits and at most 2 decimal places")
    @PositiveOrZero(message = "availableAmount should be equal or greater than zero")
    @Schema(description = "Amount available against a card", example = "90000")
    @JsonFormat(shape = NUMBER, pattern = "0.00")
    BigDecimal availableAmount

) {}
