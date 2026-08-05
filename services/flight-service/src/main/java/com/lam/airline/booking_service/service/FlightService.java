package com.lam.airline.booking_service.service;

import com.lam.airline.booking_service.dto.FlightRequest;
import com.lam.airline.booking_service.dto.FlightResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FlightService {

    FlightResponse createFlight(
            FlightRequest request
    );

    Page<FlightResponse> getAllFlights(int page, int size, String sortBy);

    FlightResponse getFlightById(Long id);

    List<FlightResponse> searchFlights(
            String source,
            String destination
    );
}