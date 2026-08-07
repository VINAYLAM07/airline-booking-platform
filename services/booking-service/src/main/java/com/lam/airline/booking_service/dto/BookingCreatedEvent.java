package com.lam.airline.booking_service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreatedEvent {

    private Long bookingId;
    private Long flightId;
    private String passengerName;
    private Integer seats;
}