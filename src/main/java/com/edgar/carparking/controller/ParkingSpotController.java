package com.edgar.carparking.controller;

import com.edgar.carparking.dto.ParkingSpotItemResponse;
import com.edgar.carparking.dto.ParkingSpotResponse;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.service.AuthService;
import com.edgar.carparking.service.CommunityService;
import com.edgar.carparking.service.ParkingSpotService;
import com.edgar.carparking.service.ResidentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/parking-spots")
@AllArgsConstructor
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;
    private final CommunityService communityService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ParkingSpotResponse> getAvailableParkingSpots() {
        Resident resident = authService.getCurrentResident();
        Community community = communityService.getCommunityById(resident.getCommunity().getId());
        return new ResponseEntity<>(parkingSpotService.getAllParkingSpots(community), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotItemResponse> getParkingSpotById(@PathVariable Long id) {
        Resident resident = authService.getCurrentResident();
        return new ResponseEntity<>(parkingSpotService.getParkingSpotById(id, resident.getCommunity().getId()), OK);
    }
}
