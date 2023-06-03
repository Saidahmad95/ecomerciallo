package com.example.ecommerciallo.conrollers.auth;

import com.example.ecommerciallo.payloads.request.SignUpDto;
import com.example.ecommerciallo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.status(CREATED).body(authService.signUp(signUpDto));
    }

    @PostMapping("/login")
    public void login() {

    }
}
