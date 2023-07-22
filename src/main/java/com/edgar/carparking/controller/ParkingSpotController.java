package com.edgar.carparking.controller;

import com.edgar.carparking.dto.ParkingSpotItemResponse;
import com.edgar.carparking.dto.ParkingSpotResponse;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.service.CommunityService;
import com.edgar.carparking.service.ParkingSpotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/parking-spots")
@AllArgsConstructor
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;
    private final CommunityService communityService;

    @GetMapping
    public ResponseEntity<ParkingSpotResponse> getAvailableParkingSpots(@RequestParam Long communityId) {
        Community community = communityService.getCommunityById(communityId);
        return new ResponseEntity<>(parkingSpotService.getAllParkingSpots(community), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotItemResponse> getParkingSpotById(@PathVariable Long id) {
        return new ResponseEntity<>(parkingSpotService.getParkingSpotById(id), OK);
    }
}
