package com.lam.airline.payment_service.service.impl;

import com.lam.airline.common.events.BookingCreatedEvent;
import com.lam.airline.payment_service.entity.Payment;
import com.lam.airline.payment_service.repository.PaymentRepository;
import com.lam.airline.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public void processPayment(
            BookingCreatedEvent event) {

        System.out.println(
                "Processing payment for booking "
                        + event.getBookingId());
        System.out.println("BookingId = " + event.getBookingId());
        System.out.println("FlightId = " + event.getFlightId());
        System.out.println("Passenger = " + event.getPassengerName());
        System.out.println("Seats = " + event.getSeats());

        Payment payment = Payment.builder()
                .bookingId(event.getBookingId())
                .flightId(event.getFlightId())
                .passengerName(event.getPassengerName())
                .seats(event.getSeats())
                .status("SUCCESS")
                .build();

        repository.save(payment);

        System.out.println("Payment Completed");
    }
}