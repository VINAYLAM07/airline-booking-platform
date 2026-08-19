package com.lam.airline.booking_service.producer;

import com.lam.airline.common.events.ReleaseSeatEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReleaseSeatEventProducer {

    private final KafkaTemplate<String, ReleaseSeatEvent> kafkaTemplate;

    public void publish(ReleaseSeatEvent event) {

        kafkaTemplate.send(
                "release-seat",
                event
        );

        System.out.println(
                "Published release seat event : "
                        + event.getBookingId()
        );
    }
}