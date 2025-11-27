package com.josepedevs.pcrepair.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppPropertiesReaderTest {

    @Test
    void defaultValues_GivenNewInstance_ThenValuesAreCorrect() {
        final var props = AppPropertiesReader.builder().build();

        assertAll(
                () -> assertEquals("persons.txt", props.getOutputFile()),
                () -> assertEquals("output", props.getOutputDirectory()),
                () -> assertEquals(",", props.getDelimiter()),
                () -> assertEquals("csv", props.getExportFormat()),
                () -> assertTrue(props.isIncludeHeaders()),
                () -> assertEquals(500, props.getChunkSize())
        );
    }

    @Test
    void settersAndGetters_GivenValues_ThenValuesReturnedCorrectly() {
        final var props = AppPropertiesReader.builder().build();

        props.setOutputDirectory("/tmp");
        props.setOutputFile("file.csv");
        props.setDelimiter(";");
        props.setIncludeHeaders(false);
        props.setChunkSize(100);
        props.setExportFormat("json");

        assertAll(
                () -> assertEquals("/tmp", props.getOutputDirectory()),
                () -> assertEquals("file.csv", props.getOutputFile()),
                () -> assertEquals(";", props.getDelimiter()),
                () -> assertEquals("json", props.getExportFormat()),
                () -> assertFalse(props.isIncludeHeaders()),
                () -> assertEquals(100, props.getChunkSize())
        );
    }

    @Test
    void builder_GivenCustomValues_ThenValuesAreSetCorrectly() {
        final var props = AppPropertiesReader.builder()
                .outputDirectory("/data")
                .outputFile("export.csv")
                .delimiter("|")
                .includeHeaders(false)
                .chunkSize(250)
                .exportFormat("txt")
                .build();

        assertAll(
                () -> assertEquals("/data", props.getOutputDirectory()),
                () -> assertEquals("export.csv", props.getOutputFile()),
                () -> assertEquals("|", props.getDelimiter()),
                () -> assertEquals("txt", props.getExportFormat()),
                () -> assertFalse(props.isIncludeHeaders()),
                () -> assertEquals(250, props.getChunkSize())
        );
    }

    @Test
    void builder_GivenNoValues_ThenDefaultsApplied() {
        final var props = AppPropertiesReader.builder().build();

        assertAll(
                () -> assertEquals("output", props.getOutputDirectory()),
                () -> assertEquals("persons.txt", props.getOutputFile()),
                () -> assertEquals(",", props.getDelimiter()),
                () -> assertEquals("csv", props.getExportFormat()),
                () -> assertTrue(props.isIncludeHeaders()),
                () -> assertEquals(500, props.getChunkSize())
        );
    }
}
