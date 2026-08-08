package com.lam.airline.payment_service.service;

import com.lam.airline.common.events.BookingCreatedEvent;

public interface PaymentService {

    void processPayment(
            BookingCreatedEvent event
    );

}