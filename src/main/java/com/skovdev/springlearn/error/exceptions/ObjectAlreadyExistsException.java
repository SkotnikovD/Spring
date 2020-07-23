package com.skovdev.springlearn.error.exceptions;

/**
 * Raise this exception when client is trying to create object that already exists.
 * Example: client's trying to create user with login that already in use
 */
public class ObjectAlreadyExistsException extends RuntimeException{
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
