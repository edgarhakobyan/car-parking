package com.edgar.carparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParkingSpotResponse {
    private List<ParkingSpotItemResponse> parkingSpotItems;
}
