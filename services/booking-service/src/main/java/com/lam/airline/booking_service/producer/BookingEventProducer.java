package com.lam.airline.booking_service.producer;

import com.lam.airline.common.events.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String, BookingCreatedEvent> kafkaTemplate;

    public void publish(BookingCreatedEvent event) {
        System.out.println("BK BookingId = " + event.getBookingId());
        System.out.println("BK FlightId = " + event.getFlightId());
        System.out.println("BK Passenger = " + event.getPassengerName());
        System.out.println("BK Seats = " + event.getSeats());
        Message<BookingCreatedEvent> message =
                MessageBuilder
                        .withPayload(event)
                        .setHeader(
                                KafkaHeaders.TOPIC,
                                "booking-created"
                        )
                        .setHeader(
                                "X-Correlation-ID",
                                MDC.get("correlationId")
                        )
                        .build();

        kafkaTemplate.send(message);
//        kafkaTemplate.send("booking-created", event);

        System.out.println(
                "Published booking event : "
                        + event.getBookingId());
    }
}