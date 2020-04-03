package com.skovdev.springlearn.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchObjectException extends RuntimeException {
    public NoSuchObjectException(String message) {
        super(message);
    }
}
