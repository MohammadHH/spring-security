package com.rrps.security.security;

import java.io.Serializable;

public class AuthenticationResponseModel implements Serializable {
    private String token;

    public AuthenticationResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
