package com.lam.airline.flight_service.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveSeatRequest {

    @Min(1)
    private Integer seats;

}