package com.lam.airline.booking_service.service;

public class FlightServiceUnavailableException extends RuntimeException {

    public FlightServiceUnavailableException(String message) {
        super(message);
    }
}