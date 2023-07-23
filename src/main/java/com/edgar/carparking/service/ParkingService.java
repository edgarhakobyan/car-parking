package com.edgar.carparking.service;

import com.edgar.carparking.dto.*;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.*;
import com.edgar.carparking.repository.BookingRepository;
import com.edgar.carparking.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final BookingRepository bookingRepository;

    public ParkingResponse getAllParkingSpots(Community community) {
        List<Parking> parkingList = parkingRepository.findByCommunityAndBookingStatus(community, BookingStatus.RELEASED);
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

    @Transactional
    public BookingResponse bookParking(BookingRequest bookingRequest, Resident resident) {
        Parking parking = parkingRepository
                .findById(bookingRequest.getParkingId())
                .orElseThrow(() -> new CarParkingException(String.format("Parking spot with id %s already booked",
                        bookingRequest.getParkingId())));
        if (!parking.getCommunity().getId().equals(resident.getCommunity().getId())) {
            throw new CarParkingException(String.format("The parking spot with id %s is not in your community with id %s",
                    bookingRequest.getParkingId(), parking.getCommunity().getId()));
        }

        Booking booking = new Booking();
        booking.setParking(parking);
        booking.setResident(resident);
        booking.setCarLicensePlate(bookingRequest.getCarNumberPlate());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());

        bookingRepository.save(booking);

        parking.setBookingStatus(BookingStatus.BOOKED);
        parkingRepository.save(parking);

        return new BookingResponse(booking.getId(), parking.getParkingNumber());
    }

    @Transactional
    public void parkParking(ParkRequest parkRequest, Resident resident) {
        Booking booking = bookingRepository
                .findById(parkRequest.getBookingId())
                .orElseThrow(() -> new CarParkingException(String.format("Booking with id %s not found", parkRequest.getBookingId())));
        Parking parking = parkingRepository
                .findById(booking.getParking().getId())
                .orElseThrow(() -> new CarParkingException(String.format(
                        "Parking spot not found with id %s", booking.getParking().getId())));

        if (!booking.getResident().getId().equals(resident.getId())) {
            throw new CarParkingException(String.format("You are not allowed to release that parking spot, resident id %s, parking resident id %s",
                    resident.getId(), booking.getResident().getId()));
        }

        parking.setBookingStatus(BookingStatus.PARKED);
        parkingRepository.save(parking);
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

        parking.setBookingStatus(BookingStatus.RELEASED);

        parkingRepository.save(parking);
    }

    private ParkingItemResponse mapTo(Parking parking) {
        return new ParkingItemResponse(parking.getId(), parking.getParkingNumber());
    }
}
