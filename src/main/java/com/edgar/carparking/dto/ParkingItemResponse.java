package com.edgar.carparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingItemResponse {
    private Long id;
    private String address;
}
