package com.jobportal.exception;

public class InvalidJobSalaryRangeException extends RuntimeException {
    public InvalidJobSalaryRangeException(String message) {
        super(message);
    }

    public InvalidJobSalaryRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
