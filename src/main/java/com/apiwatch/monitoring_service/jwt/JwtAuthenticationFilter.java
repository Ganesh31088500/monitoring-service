package com.apiwatch.monitoring_service.jwt;


import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String header = request.getHeader("Authorization");

            // Skip if Authorization header is missing
            if (header == null || !header.startsWith("Bearer ")) {

                filterChain.doFilter(request, response);
                return;

            }

            String token = header.substring(7);

            // Validate JWT
            if (!jwtService.isTokenValid(token)) {

                filterChain.doFilter(request, response);
                return;

            }

            // Extract username (email)
            String username = jwtService.extractUsername(token);

            UUID userId = jwtService.extractUserId(token);

            AuthenticatedUser principal =
                    new AuthenticatedUser(
                            userId,
                            username
                    );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            token,
                            Collections.emptyList());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request));

            // Store Authentication in Security Context
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            log.debug("Authenticated user : {}", username);

        } catch (Exception ex) {

            log.error("JWT Authentication Failed : {}", ex.getMessage());

        }

        filterChain.doFilter(request, response);

    }

}
