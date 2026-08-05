package com.lam.airline.booking_service.controller;

import com.lam.airline.booking_service.dto.BookingRequest;
import com.lam.airline.booking_service.dto.BookingResponse;
import com.lam.airline.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(
            @Valid @RequestBody BookingRequest request) {
            System.out.println("In Booking controller");
        return bookingService.createBooking(request);
    }

}