package com.challenge.up42.image.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(final String message) {
        super(message);
    }

    public InternalServerErrorException(final String message, Throwable cause) {
        super(message, cause);
    }
}
