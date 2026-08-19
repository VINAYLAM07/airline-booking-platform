package com.lam.airline.booking_service.consumer;

import com.lam.airline.common.events.PaymentCompletedEvent;
import com.lam.airline.booking_service.entity.Booking;
import com.lam.airline.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final BookingRepository repository;

    @KafkaListener(
            topics = "payment-completed",
            groupId = "booking-group"
    )
    public void consume(PaymentCompletedEvent event, @Header(
            value = "X-Correlation-ID",
            required = false
    ) String correlationId) {

        MDC.put(
                "correlationId",
                correlationId != null
                        ? correlationId
                        : "UNKNOWN"
        );

        try {
            System.out.println(
                    "Booking received payment success : "
                            + event.getBookingId()
            );

            Booking booking = repository
                    .findById(event.getBookingId())
                    .orElseThrow();
            if ("CONFIRMED".equals(booking.getStatus())) {

                System.out.println(
                        "Booking already confirmed : "
                                + booking.getId()
                );

                return;
            }

            booking.setStatus("CONFIRMED");

            repository.save(booking);

            System.out.println(
                    "Booking confirmed : "
                            + booking.getId()
            );
        } finally {
            MDC.remove("correlationId");
        }
    }
}