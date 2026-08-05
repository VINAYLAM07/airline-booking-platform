package com.lam.airline.booking_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

    @NotNull
    private Long flightId;

    @NotBlank
    private String passengerName;

    @Min(1)
    private Integer seats;
}