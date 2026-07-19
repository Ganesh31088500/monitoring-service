package com.apiwatch.monitoring_service.exceptions;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    /**
     * Error Code
     */
    private String errorCode;

    /**
     * Error Message
     */
    private String message;

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