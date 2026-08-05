package com.lam.airline.booking_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightResponse {

    private Long id;

    private Integer availableSeats;

    private String airline;

    private String source;

    private String destination;
}