package com.lam.airline.booking_service.consumer;

import com.lam.airline.booking_service.entity.Booking;
import com.lam.airline.booking_service.producer.ReleaseSeatEventProducer;
import com.lam.airline.booking_service.repository.BookingRepository;
import com.lam.airline.common.events.PaymentFailedEvent;
import com.lam.airline.common.events.ReleaseSeatEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFailedEventConsumer {

    private final BookingRepository repository;
    private final ReleaseSeatEventProducer releaseSeatEventProducer;

    @KafkaListener(
            topics = "payment-failed",
            groupId = "booking-group"
    )
    public void consume(PaymentFailedEvent event, @Header(value = "X-Correlation-ID", required = false) String correlationId) {
        System.out.println(
                "🔥 BOOKING CORRELATION ID = "
                        + correlationId
        );

        MDC.put(
                "correlationId",
                correlationId != null
                        ? correlationId
                        : "UNKNOWN"
        );

        System.out.println(
                "🔥 BOOKING MDC = "
                        + MDC.get("correlationId")
        );


        try {
            System.out.println(
                    "Payment failed for booking : "
                            + event.getBookingId()
            );

            Booking booking = repository
                    .findById(event.getBookingId())
                    .orElseThrow();

            booking.setStatus("CANCELLED");

            repository.save(booking);

            ReleaseSeatEvent releaseEvent =
                    ReleaseSeatEvent.builder()
                            .bookingId(event.getBookingId())
                            .flightId(event.getFlightId())
                            .seats(booking.getSeats())
                            .build();

            releaseSeatEventProducer.publish(releaseEvent);

            System.out.println(
                    "Booking cancelled : "
                            + booking.getId()
            );
        } finally {
            MDC.remove("correlationId");
        }
    }
}