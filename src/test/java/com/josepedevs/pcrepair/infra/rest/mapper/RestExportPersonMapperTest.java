package com.josepedevs.pcrepair.infra.rest.mapper;

import com.josepedevs.pcrepair.infra.rest.dto.PropertiesRequestDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestExportPersonMapperTest {

    private final RestExportPersonMapper mapper = Mappers.getMapper(RestExportPersonMapper.class);

    @Test
    void map_GivenValidRequest_ThenMapsFieldsCorrectly() {
        final var dto = new PropertiesRequestDTO("people.json", ";", "false", "json");

        final var result = mapper.map(dto);

        assertAll(
                () -> assertEquals("people.json", result.getOutputFile()),
                () -> assertEquals(";", result.getDelimiter()),
                () -> assertFalse(result.isIncludeHeaders()),
                () -> assertEquals("json", result.getExportFormat()),
                () -> assertEquals("output", result.getOutputDirectory()),
                () -> assertEquals(500, result.getChunkSize())
        );
    }

    @Test
    void map_GivenNullFields_ThenMapsToNullOrDefaults() {
        final var dto = new PropertiesRequestDTO(null, null, null, null);

        final var result = mapper.map(dto);

        assertAll(
                () -> assertTrue(result.isIncludeHeaders()),
                () -> assertEquals(",", result.getDelimiter()),
                () -> assertEquals("csv", result.getExportFormat()),
                () -> assertEquals("output", result.getOutputDirectory()),
                () -> assertEquals("persons.txt", result.getOutputFile()),
                () -> assertEquals(500, result.getChunkSize())
        );
    }
}
