package com.lam.airline.payment_service.producer;

import com.lam.airline.booking_service.dto.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String, BookingCreatedEvent> kafkaTemplate;

    public void publish(BookingCreatedEvent event) {

        kafkaTemplate.send(
                "booking-created",
                event
        );

        System.out.println(
                "Published booking event : "
                        + event.getBookingId());

    }

}