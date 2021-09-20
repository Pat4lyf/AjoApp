package com.contributionplatform.ajoapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message, HttpStatus status){
        super(message);
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
