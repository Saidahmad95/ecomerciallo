package com.example.ecommerciallo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MyLoginException extends RuntimeException {
    private Map<String, String> errors;

    public MyLoginException(Map<String, String> errors) {
        super();
        this.errors = errors;
    }
}
