package com.skovdev.springlearn.error.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.skovdev.springlearn.error.handler.model.ApiError;

/**
 * Filter that exclude stacktrace when serializing {@link ApiError} depend on springlearn.enableErrorStacktraces property value
 */
public class ApiErrorStacktraceFilter extends SimpleBeanPropertyFilter {

    private boolean isEnableStacktrace;

    public ApiErrorStacktraceFilter(boolean isEnableStacktrace) {
        this.isEnableStacktrace = isEnableStacktrace;
    }

    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return true;
    }

    @Override
    protected boolean include(PropertyWriter writer) {
        return true;
    }

    @Override
    public void serializeAsField(Object pojo,
                                 JsonGenerator jgen,
                                 SerializerProvider provider,
                                 PropertyWriter writer) throws Exception {
        if (pojo instanceof ApiError
                && "stacktrace".equals(writer.getName())
                && !isEnableStacktrace) {
            writer.serializeAsOmittedField(pojo, jgen, provider);
        } else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }
}
