package com.lam.airline.payment_service.consumer;

import com.lam.airline.common.events.BookingCreatedEvent;
import com.lam.airline.common.events.PaymentCompletedEvent;
import com.lam.airline.payment_service.entity.Payment;
import com.lam.airline.payment_service.producer.PaymentEventProducer;
import com.lam.airline.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final PaymentRepository repository;
    private final PaymentEventProducer producer;

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(BookingCreatedEvent event) {

        System.out.println("Payment received booking : "
                + event.getBookingId());

        Payment payment = Payment.builder()
                .bookingId(event.getBookingId())
                .flightId(event.getFlightId())
                .passengerName(event.getPassengerName())
                .seats(event.getSeats())
                .status("SUCCESS")
                .build();

        repository.save(payment);
        Payment saved = repository.save(payment);

        PaymentCompletedEvent pEvent =
                PaymentCompletedEvent.builder()
                        .paymentId(saved.getId())
                        .bookingId(saved.getBookingId())
                        .flightId(saved.getFlightId())
                        .passengerName(saved.getPassengerName())
                        .seats(saved.getSeats())
                        .status(saved.getStatus())
                        .build();

        producer.publish(pEvent);

        System.out.println("Payment saved.");
    }
}