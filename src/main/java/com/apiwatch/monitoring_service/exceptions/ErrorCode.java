package com.apiwatch.monitoring_service.exceptions;

public enum ErrorCode {

    /*
     * Success
     */
    SUCCESS("MON_200"),

    /*
     * Validation
     */
    VALIDATION_ERROR("MON_400"),

    /*
     * Authentication
     */
    UNAUTHORIZED("MON_401"),

    /*
     * Authorization
     */
    FORBIDDEN("MON_403"),

    /*
     * Not Found
     */
    MONITOR_NOT_FOUND("MON_404"),

    EXECUTION_HISTORY_NOT_FOUND("MON_405"),

    /*
     * Conflict
     */
    MONITOR_ALREADY_EXISTS("MON_409"),

    /*
     * Monitor Execution
     */
    MONITOR_EXECUTION_FAILED("MON_500"),

    /*
     * Generic
     */
    INTERNAL_SERVER_ERROR("MON_999");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}