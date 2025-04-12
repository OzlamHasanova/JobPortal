package com.jobportal.exception;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
