package com.apiwatch.monitoring_service.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    /**
     * Error Code
     */
    private String errorCode;

    /**
     * Validation Message
     */
    private String message;

    /**
     * Validation Errors
     *
     * Example:
     * {
     *   "name":"Name is required",
     *   "timeoutSeconds":"Must be greater than 0"
     * }
     */
    private Map<String, String> errors;

    /**
     * Request Path
     */
    private String path;

    /**
     * Timestamp
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}