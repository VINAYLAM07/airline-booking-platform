package com.lam.airline.notification_service.consumer;

import com.lam.airline.common.events.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {

    @KafkaListener(
            topics = "payment-completed",
            groupId = "notification-group"
    )
    public void consume(PaymentCompletedEvent event) {

        System.out.println("=================================");
        System.out.println("NOTIFICATION SERVICE");
        System.out.println("Payment completed!");
        System.out.println("Booking ID : " + event.getBookingId());
        System.out.println("Passenger  : " + event.getPassengerName());
        System.out.println("Seats      : " + event.getSeats());
        System.out.println("Status     : " + event.getStatus());
        System.out.println("Sending confirmation notification...");
        System.out.println("=================================");
    }
}
