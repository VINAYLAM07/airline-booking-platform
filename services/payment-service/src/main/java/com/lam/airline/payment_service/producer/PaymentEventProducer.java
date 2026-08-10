package com.lam.airline.payment_service.producer;

import com.lam.airline.common.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public void publish(PaymentCompletedEvent event) {

        kafkaTemplate.send(
                "payment-completed",
                event
        );

        System.out.println(
                "Published payment completed event : "
                        + event.getPaymentId());

    }
}