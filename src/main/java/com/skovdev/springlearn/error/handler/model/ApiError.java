package com.skovdev.springlearn.error.handler.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFilter("ApiErrorStacktraceFilter")
public class ApiError {

    private HttpStatus status;

    private String clientMessage;

    @JsonIgnore
    private Throwable exception;

    private String debugMessage;

    private List<FieldValidationError> fieldValidationErrors;

    public ApiError(HttpStatus status, String clientMessage, @Nullable Throwable exception) {
        this.status = status;
        this.clientMessage = clientMessage;
        this.exception = exception;
        if (exception != null) this.debugMessage = exception.getMessage();
    }

    public void addFieldValidationErrors(List<FieldError> fieldErrors) {
        fieldValidationErrors = fieldErrors.stream()
                .map(fe -> new FieldValidationError(fe.getObjectName(), fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @JsonProperty(value = "exceptionName")
    public String getExceptionName() {
        return exception != null ? exception.getClass().getName() : null;
    }

    @JsonProperty(value = "stacktrace")
    public String getStackTrace() {
        return exception != null ? ExceptionUtils.getStackTrace(exception) : null;
    }

}
