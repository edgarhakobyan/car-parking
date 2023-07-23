package com.edgar.carparking.controller;

import com.edgar.carparking.dto.ParkingItemResponse;
import com.edgar.carparking.dto.ParkingResponse;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.service.AuthService;
import com.edgar.carparking.service.CommunityService;
import com.edgar.carparking.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/parking")
@AllArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;
    private final CommunityService communityService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ParkingResponse> getAvailableParkingSpots() {
        Resident resident = authService.getCurrentResident();
        Community community = communityService.getCommunityById(resident.getCommunity().getId());
        return new ResponseEntity<>(parkingService.getAllParkingSpots(community), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingItemResponse> getParkingById(@PathVariable Long id) {
        Resident resident = authService.getCurrentResident();
        return new ResponseEntity<>(parkingService.getParkingById(id, resident.getCommunity().getId()), OK);
    }
}
