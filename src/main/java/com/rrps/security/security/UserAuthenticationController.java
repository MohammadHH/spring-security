package com.rrps.security.security;

import com.rrps.security.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {
    private MyUserDetailsService service;
    private JwtUtils utils;
    private AuthenticationManager manager;

    public UserAuthenticationController(MyUserDetailsService service, JwtUtils utils, AuthenticationManager manager) {
        this.service = service;
        this.utils = utils;
        this.manager = manager;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody AuthenticationRequestModel user) {
        return service.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestModel user) throws Exception {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = service.loadUserByUsername(user.getEmail());
        final String token = utils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseModel(token));
    }

}
