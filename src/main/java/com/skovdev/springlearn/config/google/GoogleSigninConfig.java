package com.skovdev.springlearn.config.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleSigninConfig {

    @Bean
    HttpTransport createHttpFactory() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    JsonFactory createJsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }
}
