package com.lam.airline.booking_service.client;


import com.lam.airline.booking_service.dto.FlightResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/flights/{id}")
    FlightResponse getFlight(
            @PathVariable Long id
    );

}