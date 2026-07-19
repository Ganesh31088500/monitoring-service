package com.apiwatch.monitoring_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignExceptionConfig {

    @Bean
    public ErrorDecoder errorDecoder() {

        return new ErrorDecoder.Default();

    }

}