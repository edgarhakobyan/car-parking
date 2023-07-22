package com.edgar.carparking.controller;

import com.edgar.carparking.dto.AuthenticationResponse;
import com.edgar.carparking.dto.LoginRequest;
import com.edgar.carparking.dto.RegistrationRequest;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.service.AuthService;
import com.edgar.carparking.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.resolve;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest request) {
        authService.signUp(request);
        return new ResponseEntity<>("User successfully registered", CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
