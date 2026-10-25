package dev.carv.bank.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatusCode;

@Schema(
    name = "Response",
    description = "Schema to hold successful response information")
public record ResponseDto(

    @Schema(description = "Response status")
    HttpStatusCode status,

    @Schema(description = "Response message", example = "Response message")
    String message

) {
}
