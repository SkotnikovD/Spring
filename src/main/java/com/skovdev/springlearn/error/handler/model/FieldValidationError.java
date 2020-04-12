package com.skovdev.springlearn.error.handler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldValidationError {

    private String object;

    private String field;

    private Object rejectedValue;

    private String message;
}
