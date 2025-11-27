package com.josepedevs.pcrepair.application.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class ExportJobParameterCreatorServiceTest {

    private final ExportJobParameterCreatorService service = new ExportJobParameterCreatorService();

    @Test
    void prepareJobParameters_GivenValidProps_ThenReturnsExpectedJobParameters() {
        final var props = AppPropertiesReader.builder()
                .outputFile("people.csv")
                .delimiter(";")
                .includeHeaders(true)
                .exportFormat("csv")
                .build();

        final var result = service.prepareJobParameters(props);

        assertAll(
                () -> assertEquals("people.csv", result.getString("outputFileName")),
                () -> assertEquals(";", result.getString("delimiter")),
                () -> assertEquals("true", result.getString("includeHeaders")),
                () -> assertEquals("csv", result.getString("exportFormat")),
                () -> assertNotNull(result.getString("run.id")),
                () -> assertFalse(
                        Objects.requireNonNull(result.getString("run.id")).isBlank()));
    }

    @Test
    void prepareJobParameters_GivenNullProps_ThenReturnsDefaultJobParameters() {
        final var result = service.prepareJobParameters(null);

        assertAll(
                () -> assertEquals(",", result.getString("delimiter")),
                () -> assertEquals("persons.txt", result.getString("outputFileName")),
                () -> assertEquals("true", result.getString("includeHeaders")),
                () -> assertEquals("csv", result.getString("exportFormat")),
                () -> assertNotNull(result.getString("run.id")),
                () -> assertFalse(
                        Objects.requireNonNull(result.getString("run.id")).isBlank()));
    }
}
