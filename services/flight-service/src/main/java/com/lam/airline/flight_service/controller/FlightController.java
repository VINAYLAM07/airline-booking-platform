package com.lam.airline.flight_service.controller;

import com.lam.airline.flight_service.dto.FlightRequest;
import com.lam.airline.flight_service.dto.FlightResponse;
import com.lam.airline.flight_service.dto.ReserveSeatRequest;
import com.lam.airline.flight_service.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public FlightResponse createFlight(@Valid
            @RequestBody FlightRequest request) {

        return flightService.createFlight(request);
    }

    @GetMapping
    public Page<FlightResponse> getAllFlights(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "id")
            String sortBy)
    {

        return flightService.getAllFlights(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public FlightResponse getFlight(
            @PathVariable Long id) {

        return flightService.getFlightById(id);
    }

    @GetMapping("/search")
    public List<FlightResponse> searchFlights(
            @RequestParam String source,
            @RequestParam String destination) {

        return flightService.searchFlights(
                source,
                destination
        );
    }

    @PostMapping("/{id}/reserve")
    public void reserveSeats(@PathVariable Long id, @RequestBody ReserveSeatRequest request) {
        System.out.println("In Flight controller");
        flightService.reserveSeats(id, request.getSeats());
        // Log the latest available seats count
        int availableSeats = flightService.getFlightById(id).getAvailableSeats();
        System.out.println(id+" ==== "+availableSeats);
    }
}