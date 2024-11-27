package com.kamiloses.authservice.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginDetails {

    private String username;
    private String password;

    // Getters and setters
}