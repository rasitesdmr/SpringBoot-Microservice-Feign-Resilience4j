package com.example.userservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class FirstAndLastNameAlreadyExistsException extends RuntimeException {
    public FirstAndLastNameAlreadyExistsException(String s) {
        super(s);
    }


}
