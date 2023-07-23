package com.edgar.carparking.service;

import com.edgar.carparking.dto.AuthenticationResponse;
import com.edgar.carparking.dto.LoginRequest;
import com.edgar.carparking.dto.RegistrationRequest;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.repository.ResidentRepository;
import com.edgar.carparking.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final CommunityService communityService;
    private final ResidentRepository residentRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(RegistrationRequest request) {
        Community community = communityService.getCommunityById(request.getCommunityId());

        Resident resident = new Resident();
        resident.setUsername(request.getUsername());
        resident.setPassword(passwordEncoder.encode(request.getPassword()));
        resident.setCommunity(community);

        residentRepository.save(resident);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return new AuthenticationResponse(token);
    }

    @Transactional(readOnly = true)
    public Resident getCurrentResident() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return residentRepository
                .findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }
}
