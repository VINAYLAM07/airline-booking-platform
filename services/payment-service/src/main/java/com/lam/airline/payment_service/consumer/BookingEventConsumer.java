package com.lam.airline.payment_service.consumer;

import com.lam.airline.common.events.BookingCreatedEvent;
import com.lam.airline.payment_service.entity.Payment;
import com.lam.airline.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final PaymentRepository repository;

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(BookingCreatedEvent event) {

        System.out.println("Payment received booking : "
                + event.getBookingId());

        Payment payment = Payment.builder()
                .bookingId(event.getBookingId())
                .flightId(event.getFlightId())
                .passengerName(event.getPassengerName())
                .status("SUCCESS")
                .build();

        repository.save(payment);

        System.out.println("Payment saved.");
    }
}