package com.edgar.carparking.controller;

import com.edgar.carparking.dto.BookingRequest;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.service.AuthService;
import com.edgar.carparking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> bookParkingSpot(@RequestBody BookingRequest bookingRequest) {
        Resident resident = authService.getCurrentResident();
        Long bookingId = bookingService.bookParkingSpot(bookingRequest.getParkingSpotId(), resident);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(bookingId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> releaseBookingSpot(@PathVariable Long id) {
        Resident resident = authService.getCurrentResident();
        bookingService.releaseParkingSpot(id, resident);
        return ResponseEntity.ok().build();
    }

}
