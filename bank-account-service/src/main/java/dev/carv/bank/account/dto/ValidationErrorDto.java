package dev.carv.bank.account.dto;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public record ValidationErrorDto(
    String field,
    String message
) {

    public ValidationErrorDto(ObjectError error) {
        this(((FieldError)error).getField(), error.getDefaultMessage());
    }

}
