package com.skovdev.springlearn.config;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.skovdev.springlearn.error.handler.ApiErrorStacktraceFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder(@Value("${springlearn.enableErrorStacktraces}") boolean isStacktracesEnabled) {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        SimpleFilterProvider apiErrorStacktraceFilter = new SimpleFilterProvider();
        apiErrorStacktraceFilter.addFilter("ApiErrorStacktraceFilter", new ApiErrorStacktraceFilter(isStacktracesEnabled));
        b.filters(apiErrorStacktraceFilter);
        return b;
    }
}
