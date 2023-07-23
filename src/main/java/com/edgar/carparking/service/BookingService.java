package com.edgar.carparking.service;

import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Booking;
import com.edgar.carparking.model.ParkingSpot;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.repository.BookingRepository;
import com.edgar.carparking.repository.ParkingSpotRepository;
import com.edgar.carparking.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ResidentRepository residentRepository;

    @Transactional
    public Long bookParkingSpot(Long parkingSpotId, Resident resident) {
        ParkingSpot parkingSpot = parkingSpotRepository
                .findById(parkingSpotId)
                .orElseThrow(() -> new CarParkingException(String.format("Parking spot with id %s already booked", parkingSpotId)));
        if (!parkingSpot.getCommunity().getId().equals(resident.getCommunity().getId())) {
            throw new CarParkingException(String.format("The parking spot with id %s is not in your community with id %s",
                    parkingSpotId, parkingSpot.getCommunity().getId()));
        }

        Booking booking = new Booking();
        booking.setParkingSpot(parkingSpot);
        booking.setResident(resident);
        booking.setStartTime(LocalDateTime.now());

        bookingRepository.save(booking);

        parkingSpot.setAvailable(false);
        parkingSpotRepository.save(parkingSpot);

        return booking.getId();
    }

    @Transactional
    public void releaseParkingSpot(Long bookingId, Resident resident) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new CarParkingException(String.format("Booking with id %s not found", bookingId)));
        ParkingSpot parkingSpot = parkingSpotRepository
                .findById(booking.getParkingSpot().getId())
                .orElseThrow(() -> new CarParkingException(String.format(
                        "Parking spot not found with id %s", booking.getParkingSpot().getId())));

        if (!booking.getResident().getId().equals(resident.getId())) {
            throw new CarParkingException(String.format("You are not allowed to release that parking spot, resident id %s, parking resident id %s",
                    resident.getId(), booking.getResident().getId()));
        }

        booking.setEndTime(LocalDateTime.now());

        parkingSpot.setAvailable(true);

        bookingRepository.save(booking);
        parkingSpotRepository.save(parkingSpot);
    }
}
