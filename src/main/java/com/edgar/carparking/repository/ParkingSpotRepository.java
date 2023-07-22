package com.edgar.carparking.repository;

import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByIsAvailable(Boolean isAvailable);
    List<ParkingSpot> findByCommunity(Community community);

    List<ParkingSpot> findByCommunityAndIsAvailable(Community community, Boolean isAvailable);
}
