package com.apiwatch.monitoring_service.exceptions;

public class MonitorAlreadyExistsException
extends RuntimeException {

public MonitorAlreadyExistsException(String message) {
super(message);
}

}