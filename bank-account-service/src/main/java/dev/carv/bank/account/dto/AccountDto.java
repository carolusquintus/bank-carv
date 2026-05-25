package dev.carv.bank.account.dto;

public record AccountDto(
    Long number,
    String type,
    String branchAddress
) {}
