package com.example.ecommerciallo.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private String phone;

    private Set<String> roles;
}
