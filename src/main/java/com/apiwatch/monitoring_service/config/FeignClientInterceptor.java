package com.apiwatch.monitoring_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;

@Configuration
public class FeignClientInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            Authentication authentication =
                    SecurityContextHolder.getContext()
                            .getAuthentication();

            if (authentication == null) {

                System.out.println("Authentication is NULL");
                return;
            }

            System.out.println("Principal : "
                    + authentication.getPrincipal());

            System.out.println("Credentials : "
                    + authentication.getCredentials());

            if (authentication.getCredentials() instanceof String token) {

                requestTemplate.header(
                        "Authorization",
                        "Bearer " + token);

                System.out.println("JWT forwarded to API Service");

            } else {

                System.out.println("JWT NOT forwarded");

            }

        };

    }

}