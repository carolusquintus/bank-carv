package dev.carv.bank.account.exception;

import dev.carv.bank.account.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static dev.carv.bank.account.constant.ResponseMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler {

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
