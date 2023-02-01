package com.example.userservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorResponse {
    @ExceptionHandler(FirstAndLastNameAlreadyExistsException.class)
    public ResponseEntity<?> handle(FirstAndLastNameAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> handle(DepartmentNotFoundException exception) {
       // return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().getStatus()));
          return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
}
