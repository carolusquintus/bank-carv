package dev.carv.bank.account.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS(OK, "Request processed successfully"),
    ACCOUNT_CREATED(CREATED, "Account created successfully"),
    CUSTOMER_ALREADY_EXISTS(BAD_REQUEST, "Customer already registered with given %s: %s"),
    CUSTOMER_VALIDATION(BAD_REQUEST, "Customer validation errors."),
    RESOURCE_NOT_FOUND(NOT_FOUND, "%s not found with given data %s: '%s'"),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "Error occurred. Please contact support");

    private final HttpStatus status;
    private final String message;

}
