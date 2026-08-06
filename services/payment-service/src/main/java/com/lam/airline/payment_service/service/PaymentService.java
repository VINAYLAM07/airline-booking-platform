package com.lam.airline.payment_service.service;

import com.lam.airline.payment_service.dto.BookingCreatedEvent;

public interface PaymentService {

    void processPayment(
            BookingCreatedEvent event
    );

}