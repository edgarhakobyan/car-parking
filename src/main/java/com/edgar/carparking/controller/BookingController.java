package com.edgar.carparking.controller;

import com.edgar.carparking.dto.BookingRequest;
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

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> bookParkingSpot(@RequestBody BookingRequest bookingRequest) {
        Long userId = 1L;
        Long bookingId = bookingService.bookParkingSpot(bookingRequest.getParkingSpotId(), userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(bookingId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> releaseBookingSpot(@PathVariable Long id) {
        bookingService.releaseParkingSpot(id);
        return ResponseEntity.ok().build();
    }

}
