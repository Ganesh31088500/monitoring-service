package com.apiwatch.monitoring_service.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Base64 encoded secret key
     */
    private String secret;

    /**
     * JWT issuer
     */
    private String issuer;

    /**
     * Access token expiration (milliseconds)
     */
    private long accessTokenExpiration;

    /**
     * Refresh token expiration (milliseconds)
     */
    private long refreshTokenExpiration;

}
