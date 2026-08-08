package com.lam.airline.common.events;

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