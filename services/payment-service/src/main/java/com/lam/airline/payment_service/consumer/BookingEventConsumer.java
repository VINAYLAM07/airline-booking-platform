package com.lam.airline.payment_service.consumer;

import com.lam.airline.payment_service.dto.BookingCreatedEvent;
import com.lam.airline.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(
            BookingCreatedEvent event) {

        System.out.println(
                "====== Received Booking Event : "
                        + event.getBookingId());

        paymentService.processPayment(event);
    }
}