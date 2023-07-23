package com.edgar.carparking.service;

import com.edgar.carparking.dto.ParkingSpotItemResponse;
import com.edgar.carparking.dto.ParkingSpotResponse;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.ParkingSpot;
import com.edgar.carparking.repository.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotResponse getAllParkingSpots(Community community) {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByCommunityAndIsAvailable(community, true);
        List<ParkingSpotItemResponse> parkingSpotItemResponseList = parkingSpots
                .stream()
                .map(this::parkingSpotMapToResponse)
                .toList();
        return new ParkingSpotResponse(parkingSpotItemResponseList);
    }

    public ParkingSpotItemResponse getParkingSpotById(Long parkingId, Long communityId) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingId)
                .orElseThrow(() -> new CarParkingException("Parking spot with id not found " + parkingId));
        if (!parkingSpot.getCommunity().getId().equals(communityId)) {
            throw new CarParkingException(String.format("The parking spot with id %s is not in your community with id %s",
                    parkingId, parkingSpot.getCommunity().getId()));
        }
        return parkingSpotMapToResponse(parkingSpot);
    }

    private ParkingSpotItemResponse parkingSpotMapToResponse(ParkingSpot parkingSpot) {
        return new ParkingSpotItemResponse(parkingSpot.getId(), parkingSpot.getAddress());
    }
}
