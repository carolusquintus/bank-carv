package dev.carv.bank.commons.constant;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum Message {

    SUCCESS(OK, "Request processed successfully"),
    RESOURCE_CREATED(CREATED, "%s created successfully"),
    RESOURCE_ALREADY_EXISTS(BAD_REQUEST, "%s already exists with given %s: %s"),
    RESOURCE_VALIDATION(BAD_REQUEST, "%s has validation errors"),
    RESOURCE_NOT_FOUND(NOT_FOUND, "%s not found with given data %s: '%s'"),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "Error occurred. Please contact support");

    private final HttpStatus status;
    private final String text;

    Message(HttpStatus status, String text) {
        this.status = status;
        this.text = text;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

}
