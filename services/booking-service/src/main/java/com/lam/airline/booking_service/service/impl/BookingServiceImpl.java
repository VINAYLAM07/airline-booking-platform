package com.lam.airline.booking_service.service.impl;

import com.lam.airline.booking_service.client.FlightClient;
import com.lam.airline.booking_service.dto.BookingRequest;
import com.lam.airline.booking_service.dto.BookingResponse;
import com.lam.airline.booking_service.dto.FlightResponse;
import com.lam.airline.booking_service.entity.Booking;
import com.lam.airline.booking_service.repository.BookingRepository;
import com.lam.airline.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final FlightClient flightClient;

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        FlightResponse flight =
                flightClient.getFlight(request.getFlightId());

        if (flight.getAvailableSeats() < request.getSeats()) {
            throw new RuntimeException("Seats not available");
        }

        Booking booking = Booking.builder()
                .flightId(request.getFlightId())
                .passengerName(request.getPassengerName())
                .seats(request.getSeats())
                .status("CONFIRMED")
                .build();

        Booking saved = repository.save(booking);

        return map(saved);
    }

    private BookingResponse map(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())
                .flightId(booking.getFlightId())
                .passengerName(booking.getPassengerName())
                .seats(booking.getSeats())
                .status(booking.getStatus())
                .build();
    }
}