package com.skovdev.springlearn.error.exceptions;

import org.springframework.http.HttpStatus;

/**
 * General exception that should be raised in API level, mainly in controller.
 */
public class RestApiException extends RuntimeException{

    private HttpStatus responseStatus;

    /**
     * @param message error message
     * @param responseStatus status of response
     */
    public RestApiException(String message, HttpStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }
}
