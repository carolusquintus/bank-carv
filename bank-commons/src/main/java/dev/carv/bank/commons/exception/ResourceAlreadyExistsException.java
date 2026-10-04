package dev.carv.bank.commons.exception;

import dev.carv.bank.commons.constant.Message;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(Message message, String... params) {
        super(message.getText().formatted(params));
    }

}
