package dev.carv.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "Response",
    description = "Schema to hold successful response information")
public record ResponseDto(

    @Schema(description = "Response status code")
    Integer statusCode,

    @Schema(description = "Response status message" )
    String statusMessage

) {
}
