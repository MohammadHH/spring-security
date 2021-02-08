package com.rrps.security.security;

import java.io.Serializable;

public class AuthenticationRequestModel implements Serializable {
    private String email;
    private String password;

    //need default constructor for JSON Parsing
    public AuthenticationRequestModel() {

    }

    public AuthenticationRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
