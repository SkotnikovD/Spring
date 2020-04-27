package com.skovdev.springlearn.error.exceptions;

import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * General exception that should be raised in API level, mainly in controller.
 */
public class RestApiException extends RuntimeException{

    private String clientMessage;
    private final HttpStatus responseStatus;

    /**
     * @param responseStatus status of response
     * @param debugMessage error message for developer, who consumes the API
     * @param clientMessage error message that should be displayed to end user
     */
    public RestApiException(@Nonnull HttpStatus responseStatus, @Nullable String debugMessage, @Nullable String clientMessage) {
        this(responseStatus, debugMessage!=null ? debugMessage : clientMessage);
        this.clientMessage = clientMessage;
    }

    /**
     * @param responseStatus status of response
     * @param clientMessage error message that should be displayed to end user
     */
    public RestApiException(@Nonnull HttpStatus responseStatus, @Nullable String clientMessage) {
        super(clientMessage);
        this.responseStatus = responseStatus;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}
