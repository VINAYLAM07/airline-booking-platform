package com.lam.airline.flight_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(
            ObjectMapper objectMapper) {

        ObjectMapper redisObjectMapper = objectMapper.copy();

        BasicPolymorphicTypeValidator validator =
                BasicPolymorphicTypeValidator.builder()
                        .allowIfSubType("com.lam.airline")
                        .allowIfSubType("java.util")
                        .allowIfSubType("java.time")
                        .allowIfSubType("java.math")
                        .build();

        redisObjectMapper.activateDefaultTyping(
                validator,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        return RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(
                                        new GenericJackson2JsonRedisSerializer(
                                                redisObjectMapper
                                        )
                                )
                );
    }
}