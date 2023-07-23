package com.edgar.carparking.service;

import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Booking;
import com.edgar.carparking.model.Parking;
import com.edgar.carparking.model.Resident;
import com.edgar.carparking.repository.BookingRepository;
import com.edgar.carparking.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ParkingRepository parkingRepository;

    @Transactional
    public Long bookParking(Long parkingId, Resident resident) {
        Parking parking = parkingRepository
                .findById(parkingId)
                .orElseThrow(() -> new CarParkingException(String.format("Parking spot with id %s already booked", parkingId)));
        if (!parking.getCommunity().getId().equals(resident.getCommunity().getId())) {
            throw new CarParkingException(String.format("The parking spot with id %s is not in your community with id %s",
                    parkingId, parking.getCommunity().getId()));
        }

        Booking booking = new Booking();
        booking.setParking(parking);
        booking.setResident(resident);
        booking.setStartTime(LocalDateTime.now());

        bookingRepository.save(booking);

        parking.setAvailable(false);
        parkingRepository.save(parking);

        return booking.getId();
    }

    @Transactional
    public void releaseParking(Long bookingId, Resident resident) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new CarParkingException(String.format("Booking with id %s not found", bookingId)));
        Parking parking = parkingRepository
                .findById(booking.getParking().getId())
                .orElseThrow(() -> new CarParkingException(String.format(
                        "Parking spot not found with id %s", booking.getParking().getId())));

        if (!booking.getResident().getId().equals(resident.getId())) {
            throw new CarParkingException(String.format("You are not allowed to release that parking spot, resident id %s, parking resident id %s",
                    resident.getId(), booking.getResident().getId()));
        }

        booking.setEndTime(LocalDateTime.now());

        parking.setAvailable(true);

        bookingRepository.save(booking);
        parkingRepository.save(parking);
    }
}
