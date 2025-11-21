package com.josepedevs.pcrepair.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileFormatsEnumTest {

    @Test
    void from_GivenCsvString_ThenReturnsCsvEnum() {
        final var result = FileFormatsEnum.from("csv");

        assertEquals(FileFormatsEnum.CSV, result);
    }

    @Test
    void from_GivenJsonString_ThenReturnsJsonEnum() {
        final var result = FileFormatsEnum.from("json");

        assertEquals(FileFormatsEnum.JSON, result);
    }

    @Test
    void from_GivenUppercaseCsvString_ThenReturnsCsvEnum() {
        final var result = FileFormatsEnum.from("CSV");

        assertEquals(FileFormatsEnum.CSV, result);
    }

    @Test
    void from_GivenUppercaseJsonString_ThenReturnsJsonEnum() {
        final var result = FileFormatsEnum.from("JSON");

        assertEquals(FileFormatsEnum.JSON, result);
    }

    @Test
    void from_GivenUnknownFormat_ThenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> FileFormatsEnum.from("xml"));
    }

    @Test
    void from_GivenNullValue_ThenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> FileFormatsEnum.from(null));
    }
}