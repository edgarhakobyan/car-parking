package com.edgar.carparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParkingResponse {
    private List<ParkingItemResponse> parkingItems;
}
