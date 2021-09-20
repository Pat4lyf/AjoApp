package com.contributionplatform.ajoapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message, HttpStatus status){
        super(message);
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
