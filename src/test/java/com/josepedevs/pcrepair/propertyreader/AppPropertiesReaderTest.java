package com.josepedevs.pcrepair.propertyreader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppPropertiesReaderTest {

    @Test
    void defaultValues_GivenNewInstance_ThenValuesAreCorrect() {
        final var props = new AppPropertiesReader();

        assertEquals("persons.txt", props.getOutputFile());
        assertEquals("output", props.getOutputDirectory());
        assertEquals(",", props.getDelimiter());
        assertEquals("csv", props.getExportFormat());
        assertTrue(props.isIncludeHeaders());
        assertEquals(500, props.getChunkSize());
    }

    @Test
    void settersAndGetters_GivenValues_ThenValuesReturnedCorrectly() {
        final var props = new AppPropertiesReader();

        props.setOutputDirectory("/tmp");
        props.setOutputFile("file.csv");
        props.setDelimiter(";");
        props.setIncludeHeaders(false);
        props.setChunkSize(100);
        props.setExportFormat("json");

        assertEquals("/tmp", props.getOutputDirectory());
        assertEquals("file.csv", props.getOutputFile());
        assertEquals(";", props.getDelimiter());
        assertEquals("json", props.getExportFormat());
        assertFalse(props.isIncludeHeaders());
        assertEquals(100, props.getChunkSize());
    }
}
