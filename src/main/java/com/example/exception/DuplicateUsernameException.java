package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUsernameException extends Exception {
    
    
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
