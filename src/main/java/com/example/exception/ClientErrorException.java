package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientErrorException extends Exception {

    public ClientErrorException(String message){
        super(message);
    }
    
}
