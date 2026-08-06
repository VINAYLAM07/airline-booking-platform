package com.lam.airline.payment_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreatedEvent {

    private Long bookingId;

    private Long flightId;

    private String passengerName;

    private Integer seats;

}