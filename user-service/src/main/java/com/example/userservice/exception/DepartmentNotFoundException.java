package com.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {

    private  ExceptionMessage exceptionMessage;


    public DepartmentNotFoundException(ExceptionMessage message) {
        this.exceptionMessage = message;
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }


    public DepartmentNotFoundException(String message, ExceptionMessage exceptionMessage) {
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}
