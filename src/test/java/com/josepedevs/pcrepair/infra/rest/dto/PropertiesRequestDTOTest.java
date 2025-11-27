package com.josepedevs.pcrepair.infra.rest.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PropertiesRequestDTOTest {

    @Test
    void constructor_GivenAllFields_ThenRecordContainsValues() {
        final var dto = new PropertiesRequestDTO("output.txt", ";", "false", "txt");

        assertAll(
                () -> assertEquals("output.txt", dto.outputFileName()),
                () -> assertEquals(";", dto.delimiter()),
                () -> assertEquals("false", dto.includeHeaders()),
                () -> assertEquals("txt", dto.exportFormat()));
    }

    @Test
    void noArgsConstructor_GivenNoParameters_ThenDefaultValuesAreAssigned() {
        final var dto = new PropertiesRequestDTO();

        assertAll(
                () -> assertEquals("persons.txt", dto.outputFileName()),
                () -> assertEquals(",", dto.delimiter()),
                () -> assertEquals("true", dto.includeHeaders()),
                () -> assertEquals("csv", dto.exportFormat()));
    }
}
