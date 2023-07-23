package com.edgar.carparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long parkingId;
    private String carNumberPlate;

    private Instant startTime;

    private Instant endTime;

}
