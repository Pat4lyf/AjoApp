package com.contributionplatform.ajoapp.exceptions;

import com.contributionplatform.ajoapp.payloads.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, exception), status);
    }
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExistsException(UserAlreadyExistsException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, exception), status);
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, exception), status);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, exception), status);
    }

    public static Response prepareErrorJSON(HttpStatus status, Exception ex) {
        Response response = new Response();
        try {
            response.setMessage(ex.getMessage());
            response.setStatusCode(status.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
