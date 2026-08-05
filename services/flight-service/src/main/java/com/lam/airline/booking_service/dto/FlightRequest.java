package com.lam.airline.booking_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotNull
    private String flightNumber;

    @NotNull
    private String airline;

    @NotNull
    private String source;

    @NotNull
    private String destination;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer totalSeats;
}