package com.apiwatch.monitoring_service.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Monitor Not Found
     */
    @ExceptionHandler(MonitorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleMonitorNotFound(
            MonitorNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.MONITOR_NOT_FOUND.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Execution History Not Found
     */
    @ExceptionHandler(ExecutionHistoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleExecutionHistoryNotFound(
            ExecutionHistoryNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.EXECUTION_HISTORY_NOT_FOUND.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Duplicate Monitor
     */
    @ExceptionHandler(MonitorAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleMonitorAlreadyExists(
            MonitorAlreadyExistsException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.MONITOR_ALREADY_EXISTS.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Monitor Execution
     */
    @ExceptionHandler(MonitorExecutionException.class)
    public ResponseEntity<ApiErrorResponse> handleMonitorExecution(
            MonitorExecutionException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.MONITOR_EXECUTION_FAILED.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Unauthorized
     */
    @ExceptionHandler(UnauthorizedMonitorAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(
            UnauthorizedMonitorAccessException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.FORBIDDEN.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Validation Errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()

                .getAllErrors()

                .forEach(error -> {

                    String field =
                            ((FieldError) error).getField();

                    String message =
                            error.getDefaultMessage();

                    errors.put(field, message);

                });

        return ResponseEntity.badRequest()
                .body(ValidationErrorResponse.builder()
                        .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                        .message("Validation Failed")
                        .errors(errors)
                        .path(request.getRequestURI())
                        .build());

    }

    /**
     * Generic Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

}