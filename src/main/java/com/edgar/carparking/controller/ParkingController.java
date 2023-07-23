package com.edgar.carparking.controller;

import com.edgar.carparking.dto.*;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.service.AuthService;
import com.edgar.carparking.service.CommunityService;
import com.edgar.carparking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/parking")
@AllArgsConstructor
@Tag(name = "Parking")
public class ParkingController {
    private final ParkingService parkingService;
    private final CommunityService communityService;
    private final AuthService authService;

    @GetMapping
    @Operation(summary = "Parking spots", description = "Get all available parking spots for authenticated user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ParkingResponse> getAvailableParkingSpots() {
        Resident resident = authService.getCurrentResident();
        Community community = communityService.getCommunityById(resident.getCommunity().getId());
        return new ResponseEntity<>(parkingService.getAllParkingSpots(community), OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Parking spot", description = "Get parking spot information based on id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ParkingItemResponse> getParkingById(@PathVariable Long id) {
        Resident resident = authService.getCurrentResident();
        return new ResponseEntity<>(parkingService.getParkingById(id, resident.getCommunity().getId()), OK);
    }


    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Book a parking", description = "Book a parking")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<BookingResponse> bookParking(@RequestBody BookingRequest bookingRequest) {
        Resident resident = authService.getCurrentResident();
        BookingResponse bookingResponse = parkingService.bookParking(bookingRequest, resident);
        return new ResponseEntity<>(bookingResponse, CREATED);
    }

    @PutMapping("/park")
    @Operation(summary = "Park a parking", description = "Park a parking")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> parkBooking(@RequestBody ParkRequest parkRequest) {
        Resident resident = authService.getCurrentResident();
        parkingService.parkParking(parkRequest, resident);
        return new ResponseEntity<>("Successfully parked", OK);
    }

    @PutMapping("/release")
    @Operation(summary = "Release a parking", description = "Release a parking")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> releaseBooking(@PathVariable Long id) {
        Resident resident = authService.getCurrentResident();
        parkingService.releaseParking(id, resident);
        return new ResponseEntity<>("Successfully released", OK);
    }
}
