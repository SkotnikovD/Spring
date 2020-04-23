package com.skovdev.springlearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Scope("prototype")
    WebClient.Builder getWebClientBuilder (){
        return WebClient.builder();
    }
}
