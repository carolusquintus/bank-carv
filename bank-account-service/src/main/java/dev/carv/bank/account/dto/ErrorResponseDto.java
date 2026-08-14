package dev.carv.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information")
public record ErrorResponseDto(

    @Schema(description = "Request path that caused the error", example = "/api/v1/customers")
    String path,

    @Schema(description = "HTTP status code", example = "400")
    HttpStatus status,

    @Schema(description = "Error object or message", example = "Validation failed for object='customerDto'. Error count: 1")
    Object error,

    @Schema(description = "Timestamp of the error occurrence", example = "2024-06-01T12:34:56")
    LocalDateTime timestamp

) {
}
