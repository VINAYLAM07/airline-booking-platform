package com.lam.airline.flight_service.consumer;

import com.lam.airline.common.events.ReleaseSeatEvent;
import com.lam.airline.flight_service.entity.Flight;
import com.lam.airline.flight_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReleaseSeatEventConsumer {

    private final FlightRepository repository;

    @KafkaListener(
            topics = "release-seat",
            groupId = "flight-group"
    )
    @CacheEvict(value = "flights", key = "#event.flightId")
    public void consume(ReleaseSeatEvent event) {

        System.out.println(
                "Releasing seats for booking : "
                        + event.getBookingId()
        );

        Flight flight = repository
                .findById(event.getFlightId())
                .orElseThrow();

        flight.setAvailableSeats(
                flight.getAvailableSeats()
                        + event.getSeats()
        );

        repository.save(flight);

        System.out.println(
                "Released "
                        + event.getSeats()
                        + " seats for flight : "
                        + event.getFlightId()
        );
    }
}