package com.lam.airline.payment_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Bean
    public JsonSerializer<Object> jsonSerializer() {
        return new JsonSerializer<>();
    }

}