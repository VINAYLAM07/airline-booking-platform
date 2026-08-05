package com.lam.airline.booking_service.service;

import com.lam.airline.booking_service.dto.BookingRequest;
import com.lam.airline.booking_service.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

}