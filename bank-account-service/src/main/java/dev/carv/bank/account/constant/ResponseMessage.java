package dev.carv.bank.account.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    CREATED(HttpStatus.CREATED.value(), "Account created successfully"),
    SUCCESS(HttpStatus.OK.value(), "Request processed successfulli"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error ocurred. Please contact support");

    private final Integer status;
    private final String message;

}
