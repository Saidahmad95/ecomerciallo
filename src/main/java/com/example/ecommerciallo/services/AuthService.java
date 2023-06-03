package com.example.ecommerciallo.services;

import com.example.ecommerciallo.entities.User;
import com.example.ecommerciallo.payloads.request.SignUpDto;
import com.example.ecommerciallo.payloads.response.UserResponse;


public interface AuthService {
    UserResponse signUp(SignUpDto signUpDto);
}
