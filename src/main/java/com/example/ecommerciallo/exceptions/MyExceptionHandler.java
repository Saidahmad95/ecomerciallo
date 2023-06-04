package com.example.ecommerciallo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({SignUpException.class})
    public ResponseEntity<Object> handleSignupException(
            SignUpException ex, WebRequest request) {
        return new ResponseEntity<Object>(
                ex.getErrors(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MyLoginException.class})
    public ResponseEntity<Object> handleLoginException(
            MyLoginException ex, WebRequest request) {
        return new ResponseEntity<Object>(
                ex.getErrors(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }


}
