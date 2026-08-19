package com.lam.airline.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private Long bookingId;

    private Long flightId;

    private String reason;
}