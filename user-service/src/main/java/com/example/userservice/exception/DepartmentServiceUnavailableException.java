package com.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DepartmentServiceUnavailableException extends RuntimeException{

    public DepartmentServiceUnavailableException() {
    }

    public DepartmentServiceUnavailableException(String message) {
        super(message);
    }

    public DepartmentServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
