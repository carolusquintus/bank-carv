package dev.carv.bank.account.exception;

import dev.carv.bank.account.dto.ErrorResponseDto;
import dev.carv.bank.account.dto.ValidationErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static dev.carv.bank.account.constant.ResponseMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final WebRequest webRequest;
    private final View error;

    public GlobalExceptionHandler(WebRequest webRequest, View error) {
        this.webRequest = webRequest;
        this.error = error;
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var validationErrors = ex.getBindingResult().getAllErrors().stream()
            .map(error -> (FieldError) error)
            .map(ValidationErrorDto::new)
            .toList();

        return new ResponseEntity<>(new ErrorResponseDto(
            webRequest.getDescription(false),
            CUSTOMER_VALIDATION.getStatus(),
            validationErrors,
            LocalDateTime.now()
        ), CUSTOMER_VALIDATION.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorResponseDto(
            webRequest.getDescription(false),
            INTERNAL_ERROR.getStatus(),
            exception.getMessage(),
            LocalDateTime.now()
        ), INTERNAL_ERROR.getStatus());
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception,
                                                                                 WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorResponseDto(
            webRequest.getDescription(false),
            CUSTOMER_ALREADY_EXISTS.getStatus(),
            exception.getMessage(),
            LocalDateTime.now()
        ), CUSTOMER_ALREADY_EXISTS.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorResponseDto(
            webRequest.getDescription(false),
            RESOURCE_NOT_FOUND.getStatus(),
            exception.getMessage(),
            LocalDateTime.now()
        ), RESOURCE_NOT_FOUND.getStatus());
    }

}
