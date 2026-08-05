package com.lam.airline.booking_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingResponse {

    private Long id;

    private Long flightId;

    private String passengerName;

    private Integer seats;

    private String status;
}