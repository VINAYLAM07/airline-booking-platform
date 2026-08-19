package com.lam.airline.payment_service.consumer;

import com.lam.airline.common.events.BookingCreatedEvent;
import com.lam.airline.common.events.PaymentCompletedEvent;
import com.lam.airline.common.events.PaymentFailedEvent;
import com.lam.airline.payment_service.entity.Payment;
import com.lam.airline.payment_service.producer.PaymentEventProducer;
import com.lam.airline.payment_service.producer.PaymentFailedEventProducer;
import com.lam.airline.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final PaymentRepository repository;
    private final PaymentEventProducer producer;
    private final PaymentFailedEventProducer failedEventProducer;

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(BookingCreatedEvent event,  @Header("X-Correlation-ID") String correlationId) {
        System.out.println(
                "🔥 PAYMENT CORRELATION ID = "
                        + correlationId
        );
        MDC.put("correlationId", correlationId);

        try {
            System.out.println("Payment received booking : "
                    + event.getBookingId());

            // TEMPORARY simulation
            boolean paymentSuccessful =
                    event.getBookingId() % 2 != 0;

            if (!paymentSuccessful) {

                String reason = "Payment declined";

                Payment payment = Payment.builder()
                        .bookingId(event.getBookingId())
                        .flightId(event.getFlightId())
                        .passengerName(event.getPassengerName())
                        .seats(event.getSeats())
                        .status("FAILED")
                        .failureReason(reason)
                        .build();

                repository.save(payment);

                System.out.println(
                        "Payment FAILED for booking : "
                                + event.getBookingId()
                );

                PaymentFailedEvent failedEvent =
                        PaymentFailedEvent.builder()
                                .bookingId(event.getBookingId())
                                .flightId(event.getFlightId())
                                .reason(reason)
                                .build();

                failedEventProducer.publish(failedEvent);

                return;
            }
            Payment payment = Payment.builder()
                    .bookingId(event.getBookingId())
                    .flightId(event.getFlightId())
                    .passengerName(event.getPassengerName())
                    .seats(event.getSeats())
                    .status("SUCCESS")
                    .build();

            Payment saved = repository.save(payment);
            System.out.println(
                    "Payment saved successfully."
            );

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
        } finally {
            MDC.remove("correlationId");
        }


    }
}