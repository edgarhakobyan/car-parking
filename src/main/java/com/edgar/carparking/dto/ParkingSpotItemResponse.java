package com.edgar.carparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSpotItemResponse {
    private Long id;
    private String address;
}
