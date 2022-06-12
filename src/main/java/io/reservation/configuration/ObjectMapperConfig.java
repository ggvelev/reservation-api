package io.reservation.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ObjectMapper} configuration and post-configuration for use across the application context.
 *
 * @author Georgi Velev
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Enables strict deserialization so that unknown properties passed
     * as part of HTTP request bodies will cause the API to throw JsonMappingException.
     *
     * @param objectMapper already configured, spring-provided, ObjectMapper instance
     * @return same object mapper with {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} enabled
     */
    @Autowired
    public ObjectMapper objectMapper(ObjectMapper objectMapper) {
        return objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}