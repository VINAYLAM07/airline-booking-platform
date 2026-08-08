package com.lam.airline.booking_service.service.impl;

import com.lam.airline.booking_service.client.FlightClient;
import com.lam.airline.common.events.BookingCreatedEvent;
import com.lam.airline.booking_service.dto.BookingRequest;
import com.lam.airline.booking_service.dto.BookingResponse;
import com.lam.airline.booking_service.dto.ReserveSeatRequest;
import com.lam.airline.booking_service.entity.Booking;
import com.lam.airline.booking_service.producer.BookingEventProducer;
import com.lam.airline.booking_service.repository.BookingRepository;
import com.lam.airline.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final FlightClient flightClient;
    private final BookingEventProducer producer;

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        ReserveSeatRequest reserveRequest =
                new ReserveSeatRequest();

        reserveRequest.setSeats(request.getSeats());
        System.out.println("Before feign client");
        flightClient.reserveSeats(
                request.getFlightId(),
                reserveRequest
        );
        System.out.println("After feign client");
        Booking booking = Booking.builder()
                .flightId(request.getFlightId())
                .passengerName(request.getPassengerName())
                .seats(request.getSeats())
                .status("CONFIRMED")
                .build();

        Booking saved = repository.save(booking);
        BookingCreatedEvent event =
                BookingCreatedEvent.builder()
                        .bookingId(saved.getId())
                        .flightId(saved.getFlightId())
                        .passengerName(saved.getPassengerName())
                        .seats(saved.getSeats())
                        .build();

        producer.publish(event);
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