package com.skovdev.springlearn.error.exceptions;

public class NoSuchObjectException extends RuntimeException {
    public NoSuchObjectException(String message) {
        super(message);
    }

    public NoSuchObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
