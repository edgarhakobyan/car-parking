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
    public Long bookParkingSpot(Long parkingSpotId, Long residentId) {
        ParkingSpot parkingSpot = parkingSpotRepository
                .findById(parkingSpotId)
                .orElseThrow(() -> new CarParkingException(String.format("Parking spot with id %s already booked", parkingSpotId)));
        Resident resident = residentRepository
                .findById(residentId)
                .orElseThrow(() -> new CarParkingException(String.format("Resident not found with id %s", residentId)));

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
    public void releaseParkingSpot(Long bookingId) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new CarParkingException(String.format("Booking with id %s not found", bookingId)));
        ParkingSpot parkingSpot = parkingSpotRepository
                .findById(booking.getParkingSpot().getId())
                .orElseThrow(() -> new CarParkingException(String.format(
                        "Parking spot not found with id %s", booking.getParkingSpot().getId())));

        booking.setEndTime(LocalDateTime.now());

        parkingSpot.setAvailable(true);

        bookingRepository.save(booking);
        bookingRepository.save(booking);parkingSpotRepository.save(parkingSpot);
    }
}
