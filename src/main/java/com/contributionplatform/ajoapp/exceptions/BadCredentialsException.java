package com.contributionplatform.ajoapp.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message, HttpStatus status){
        super(message);
    }

    public BadCredentialsException(String message){
        super(message);
    }
}
