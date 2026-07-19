package com.apiwatch.monitoring_service.exceptions;


public class UnauthorizedMonitorAccessException
        extends RuntimeException {

    public UnauthorizedMonitorAccessException(String message) {
        super(message);
    }

}
