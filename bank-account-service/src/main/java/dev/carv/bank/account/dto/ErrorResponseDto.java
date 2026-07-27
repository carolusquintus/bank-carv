package dev.carv.bank.account.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
    String path,
    HttpStatus status,
    Object error,
    LocalDateTime timestamp
) {
}
