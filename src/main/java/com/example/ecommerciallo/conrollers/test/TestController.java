package com.example.ecommerciallo.conrollers.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN','ROLE_MODERATOR'})")
    public String userApi() {
        return "<h1>This is USER API</h1>";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminApi() {
        return "<h1>This is ADMIN API</h1>";
    }


}
