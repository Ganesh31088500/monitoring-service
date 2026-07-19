package com.apiwatch.monitoring_service.jwt;


import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            return null;
        }

        AuthenticatedUser user =
                (AuthenticatedUser) authentication.getPrincipal();

        return user.userId();
    }

    public static String getCurrentUserEmail() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            return null;
        }

        AuthenticatedUser user =
                (AuthenticatedUser) authentication.getPrincipal();

        return user.email();
    }
}