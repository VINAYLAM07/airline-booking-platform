package com.lam.airline.booking_service.repository;

import com.lam.airline.booking_service.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findBySourceAndDestination(
            String source,
            String destination
    );
}