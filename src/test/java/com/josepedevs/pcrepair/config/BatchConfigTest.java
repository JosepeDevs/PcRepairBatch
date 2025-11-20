package com.josepedevs.pcrepair.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatchConfigTest {

    @Test
    void batchConfig_GivenClass_ThenIsNotNull() {
        final var config = new BatchConfig();
        assertNotNull(config);
    }

    @Test
    void batchConfig_ClassAnnotations_ThenEnableConfigurationPropertiesPresent() {
        final var annotations = BatchConfig.class.getAnnotations();
        final var hasEnableConfigurationProperties = java.util.Arrays.stream(annotations)
                .anyMatch(a -> a.annotationType().equals(EnableConfigurationProperties.class));
        assertTrue(hasEnableConfigurationProperties);
    }
}
