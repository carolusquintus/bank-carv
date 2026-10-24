package dev.carv.bank.commons.exception;

import dev.carv.bank.commons.constant.Message;
import org.springframework.web.bind.annotation.ResponseStatus;

import static dev.carv.bank.commons.constant.Message.RESOURCE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Message message, String... params) {
        super(message.format(params));
    }

    public ResourceNotFoundException(String... params) {
        this(RESOURCE_NOT_FOUND, params);
    }

}
