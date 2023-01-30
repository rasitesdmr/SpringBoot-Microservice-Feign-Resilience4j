package com.example.userservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorResponse {
    @ExceptionHandler(FirstAndLastNameAlreadyExistsException.class)
    public ResponseEntity<?> handle(FirstAndLastNameAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
