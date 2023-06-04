package com.example.ecommerciallo.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private String token;
    //    private String type="Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
