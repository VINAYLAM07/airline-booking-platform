package com.lam.airline.booking_service.producer;

import com.lam.airline.common.events.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
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
        kafkaTemplate.send("booking-created", event);

        System.out.println(
                "Published booking event : "
                        + event.getBookingId());
    }
}