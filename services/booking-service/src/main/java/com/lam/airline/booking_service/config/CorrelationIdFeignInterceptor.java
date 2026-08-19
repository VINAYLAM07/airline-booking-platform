package com.lam.airline.booking_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdFeignInterceptor
        implements RequestInterceptor {

    private static final String HEADER =
            "X-Correlation-ID";

    @Override
    public void apply(RequestTemplate template) {

        String correlationId =
                MDC.get("correlationId");

        if (correlationId != null) {

            template.header(
                    HEADER,
                    correlationId
            );
        }
    }
}