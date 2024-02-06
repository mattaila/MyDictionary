package com.mydictionary.mydictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {
    
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
