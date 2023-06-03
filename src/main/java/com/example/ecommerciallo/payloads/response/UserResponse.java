package com.example.ecommerciallo.payloads.response;

import com.example.ecommerciallo.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserResponse {

    private String username;
    private String firstname;
    private String lastname;
    private String email;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
}
