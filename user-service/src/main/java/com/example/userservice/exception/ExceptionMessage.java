package com.example.userservice.exception;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage{

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
