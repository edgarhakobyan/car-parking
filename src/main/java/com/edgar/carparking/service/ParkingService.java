package com.edgar.carparking.service;

import com.edgar.carparking.dto.ParkingItemResponse;
import com.edgar.carparking.dto.ParkingResponse;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Parking;
import com.edgar.carparking.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public ParkingResponse getAllParkingSpots(Community community) {
        List<Parking> parkingList = parkingRepository.findByCommunityAndIsAvailable(community, true);
        List<ParkingItemResponse> parkingItemResponseList = parkingList
                .stream()
                .map(this::mapTo)
                .toList();
        return new ParkingResponse(parkingItemResponseList);
    }

    public ParkingItemResponse getParkingById(Long parkingId, Long communityId) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new CarParkingException("Parking spot with id not found " + parkingId));
        if (!parking.getCommunity().getId().equals(communityId)) {
            throw new CarParkingException(String.format("The parking spot with id %s is not in your community with id %s",
                    parkingId, parking.getCommunity().getId()));
        }
        return mapTo(parking);
    }

    private ParkingItemResponse mapTo(Parking parking) {
        return new ParkingItemResponse(parking.getId(), parking.getAddress());
    }
}
