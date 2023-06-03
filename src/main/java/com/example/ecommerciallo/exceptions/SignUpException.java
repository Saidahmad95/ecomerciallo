package com.example.ecommerciallo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SignUpException extends RuntimeException {
    private Map<String, String> errors;

    public SignUpException() {
        super();
    }

    public SignUpException(Map<String, String> errors) {
        super();
        this.errors = errors;
    }

    public SignUpException(String message) {
        super(message);

    }

}
