package com.edgar.carparking.service;

import com.edgar.carparking.dto.AuthenticationResponse;
import com.edgar.carparking.dto.LoginRequest;
import com.edgar.carparking.dto.RegistrationRequest;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final CommunityService communityService;
    private final ResidentRepository residentRepository;

    @Transactional
    public void signUp(RegistrationRequest request) {
        Community community = communityService.getCommunityById(request.getCommunityId());

        Resident resident = new Resident();
        resident.setUsername(request.getUsername());
        resident.setPassword(request.getPassword());
        resident.setCommunity(community);

        residentRepository.save(resident);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Resident resident = residentRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())
                .orElseThrow(() -> new CarParkingException("Resident not found"));

        return new AuthenticationResponse(resident.getCommunity().getId(), "token");
    }
}
