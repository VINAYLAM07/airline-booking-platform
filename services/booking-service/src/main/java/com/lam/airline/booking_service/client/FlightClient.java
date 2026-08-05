package com.lam.airline.booking_service.client;


import com.lam.airline.booking_service.dto.FlightResponse;
import com.lam.airline.booking_service.dto.ReserveSeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @PostMapping("/flights/{id}/reserve")
    void reserveSeats(
            @PathVariable Long id,
            @RequestBody ReserveSeatRequest request
    );

}