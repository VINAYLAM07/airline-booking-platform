package com.lam.airline.flight_service.service;

import com.lam.airline.flight_service.dto.FlightRequest;
import com.lam.airline.flight_service.dto.FlightResponse;
import com.lam.airline.flight_service.entity.Flight;
import com.lam.airline.flight_service.exception.ResourceNotFoundException;
import com.lam.airline.flight_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository repository;

    @Override
    public FlightResponse createFlight(FlightRequest request) {

        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airline(request.getAirline())
                .source(request.getSource())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .price(request.getPrice())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .build();

        Flight saved = repository.save(flight);

        return map(saved);
    }

    @Override
    public Page<FlightResponse> getAllFlights(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return repository.findAll(pageable)
                .map(this::map);
    }

    @Override
    @Cacheable(value = "flights", key = "#id")
    public FlightResponse getFlightById(Long id) {
        System.out.println("🔥 DATABASE HIT");
        Flight flight = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Flight not found"));

        return map(flight);
    }

    @Override
    public List<FlightResponse> searchFlights(
            String source,
            String destination) {

        return repository
                .findBySourceAndDestination(source, destination)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    @CacheEvict(value = "flights", key = "#flightId")
    public void reserveSeats(
            Long flightId,
            Integer seats) {
        System.out.println("In flight  service impl");
        Flight flight =
                repository.findById(flightId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Flight not found"));

        if(flight.getAvailableSeats() < seats){
            throw new RuntimeException("Seats not available");
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - seats
        );
        System.out.println("Flight ID = " + flightId);
        System.out.println("Requested seats = " + seats);
        System.out.println("Available = " + flight.getAvailableSeats());
        repository.save(flight);


    }

    private FlightResponse map(Flight flight) {

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .source(flight.getSource())
                .destination(flight.getDestination())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .price(flight.getPrice())
                .totalSeats(flight.getTotalSeats())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }
}