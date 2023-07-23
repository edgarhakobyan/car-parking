package com.edgar.carparking.repository;

import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    List<Parking> findByIsAvailable(Boolean isAvailable);
    List<Parking> findByCommunity(Community community);

    List<Parking> findByCommunityAndIsAvailable(Community community, Boolean isAvailable);
}
