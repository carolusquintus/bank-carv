package dev.carv.bank.commons.constant;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ResponseMessage {

    SUCCESS(OK, "Request processed successfully"),
    RESOURCE_CREATED(CREATED, "%s created successfully"),
    RESOURCE_ALREADY_EXISTS(BAD_REQUEST, "%s already exists with given %s: %s"),
    RESOURCE_VALIDATION(BAD_REQUEST, "%s has validation errors"),
    RESOURCE_NOT_FOUND(NOT_FOUND, "%s not found with given data %s: '%s'"),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "Error occurred. Please contact support");

    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
