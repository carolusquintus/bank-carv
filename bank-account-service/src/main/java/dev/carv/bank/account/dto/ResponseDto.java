package dev.carv.bank.account.dto;

public record ResponseDto(
    Integer statusCode,
    String statusMessage
) {
}
