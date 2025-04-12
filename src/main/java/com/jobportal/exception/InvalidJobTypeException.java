package com.jobportal.exception;

public class InvalidJobTypeException extends RuntimeException {
    public InvalidJobTypeException(String message) {
        super(message);
    }
    public InvalidJobTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
