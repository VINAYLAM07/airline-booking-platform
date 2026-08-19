package com.lam.airline.payment_service.producer;

import com.lam.airline.common.events.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
@Component
@RequiredArgsConstructor
public class PaymentFailedEventProducer {

    private final KafkaTemplate<String, PaymentFailedEvent> kafkaTemplate;

    public void publish(PaymentFailedEvent event) {
        Message<PaymentFailedEvent> message =
                MessageBuilder
                        .withPayload(event)
                        .setHeader(
                                KafkaHeaders.TOPIC,
                                "payment-failed"
                        )
                        .setHeader(
                                "X-Correlation-ID",
                                MDC.get("correlationId")
                        )
                        .build();
        System.out.println(
                "🔥 FAILED PRODUCER CORRELATION ID = "
                        + MDC.get("correlationId")
        );

        System.out.println(
                "🔥 HEADER BEFORE SEND = "
                        + message.getHeaders().get("X-Correlation-ID")
        );
        kafkaTemplate.send(message);

        System.out.println(
                "Published payment failed event : "
                        + event.getBookingId()
        );
    }
}