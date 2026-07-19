package com.apiwatch.monitoring_service.jwt;

import java.util.UUID;

public record AuthenticatedUser(

        UUID userId,

        String email

) {
}