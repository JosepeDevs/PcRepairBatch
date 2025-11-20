package com.josepedevs.pcrepair.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeciderValuesEnumTest {

    @Test
    void deciderValuesEnum_GivenEnumConstants_ThenDeciderValueCorrect() {
        final var includeHeaders = DeciderValuesEnum.INCLUDE_HEADERS;
        final var skipHeaders = DeciderValuesEnum.SKIP_HEADERS;

        assertEquals("INCLUDE_HEADERS", includeHeaders.getDeciderValue());
        assertEquals("SKIP_HEADERS", skipHeaders.getDeciderValue());
    }

    @Test
    void deciderValuesEnum_ValuesArray_ThenContainsAllEnums() {
        final var values = DeciderValuesEnum.values();

        assertArrayEquals(new DeciderValuesEnum[]{DeciderValuesEnum.INCLUDE_HEADERS, DeciderValuesEnum.SKIP_HEADERS}, values);
    }
}
