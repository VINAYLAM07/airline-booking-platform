package com.lam.airline.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSeatEvent {

    private Long bookingId;

    private Long flightId;

    private Integer seats;
}