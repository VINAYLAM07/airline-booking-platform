package com.lam.airline.booking_service.service;

import com.lam.airline.booking_service.client.FlightClient;
import com.lam.airline.booking_service.dto.ReserveSeatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightReservationService {

    private final FlightClient flightClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public void reserveSeats(Long flightId, ReserveSeatRequest request) {

        circuitBreakerFactory
                .create("flightService")
                .run(
                        () -> {
                            flightClient.reserveSeats(flightId, request);
                            return null;
                        },
                        this::flightServiceUnavailable
                );
    }

    private Void flightServiceUnavailable(Throwable throwable) {

        System.out.println(
                "Flight Service unavailable: "
                        + throwable.getMessage()
        );

        throw new FlightServiceUnavailableException(
                "Flight service is temporarily unavailable. Please try again."
        );
    }
}