package com.lam.airline.booking_service.consumer;

import com.lam.airline.common.events.PaymentCompletedEvent;
import com.lam.airline.booking_service.entity.Booking;
import com.lam.airline.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final BookingRepository repository;

    @KafkaListener(
            topics = "payment-completed",
            groupId = "booking-group"
    )
    public void consume(PaymentCompletedEvent event) {

        System.out.println(
                "Booking received payment success : "
                        + event.getBookingId()
        );

        Booking booking = repository
                .findById(event.getBookingId())
                .orElseThrow();

        booking.setStatus("CONFIRMED");

        repository.save(booking);

        System.out.println(
                "Booking confirmed : "
                        + booking.getId()
        );
    }
}