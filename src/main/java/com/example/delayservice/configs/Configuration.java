package com.example.delayservice.configs;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vladimir Krasnov
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
