package com.lam.airline.flight_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FlightResponse {

    private Long id;

    private String flightNumber;

    private String airline;

    private String source;

    private String destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal price;

    private Integer totalSeats;

    private Integer availableSeats;
}