package com.jobportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<String> handleJobNotFoundException(JobNotFoundException ex) {
        return new ResponseEntity<>("Job not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidJobTypeException.class)
    public ResponseEntity<String> handleInvalidJobTypeException(InvalidJobTypeException ex) {
        return new ResponseEntity<>("Invalid job type: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJobSalaryRangeException.class)
    public ResponseEntity<String> handleInvalidJobSalaryRangeException(InvalidJobSalaryRangeException ex) {
        return new ResponseEntity<>("Invalid job salary range: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
