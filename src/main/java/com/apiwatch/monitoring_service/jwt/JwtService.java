package com.apiwatch.monitoring_service.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtProperties properties;

    /**
     * Validate JWT Token
     */
    public boolean isTokenValid(String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception ex) {

            log.error("JWT Validation Failed : {}", ex.getMessage());

            return false;

        }

    }

    /**
     * Extract Username (Email)
     */
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();

    }

    /**
     * Extract User Id
     */
    public UUID extractUserId(String token) {

        Claims claims = extractClaims(token);

        Object value = claims.get("userId");

        if (value == null) {
            return null;
        }

        return UUID.fromString(value.toString());

    }

    /**
     * Extract Roles
     */
    public Object extractRoles(String token) {

        return extractClaims(token).get("roles");

    }

    /**
     * Extract Issuer
     */
    public String extractIssuer(String token) {

        return extractClaims(token).getIssuer();

    }

    /**
     * Extract Expiration
     */
    public Date extractExpiration(String token) {

        return extractClaims(token).getExpiration();

    }

    /**
     * Check Token Expiry
     */
    public boolean isExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }

    /**
     * Current Logged-in User Id
     */
    public UUID getCurrentUserId(String token) {

        return extractUserId(token);

    }

    /**
     * Current Logged-in Email
     */
    public String getCurrentUsername(String token) {

        return extractUsername(token);

    }

    /**
     * Parse JWT Claims
     */
    private Claims extractClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    /**
     * Signing Key
     */
    private SecretKey getSigningKey() {

    	 byte[] keyBytes =
    	            Base64.getDecoder().decode(properties.getSecret());

    	    return Keys.hmacShaKeyFor(keyBytes);

    }

}