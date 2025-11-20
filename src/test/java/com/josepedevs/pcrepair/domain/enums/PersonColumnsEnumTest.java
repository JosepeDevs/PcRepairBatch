package com.josepedevs.pcrepair.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonColumnsEnumTest {

    @Test
    void personColumnsEnum_GivenEnumConstants_ThenColumnNamesCorrect() {
        final var idUser = PersonColumnsEnum.ID_USER;
        final var metadata = PersonColumnsEnum.METADATA;
        final var name = PersonColumnsEnum.NAME;
        final var nidPassport = PersonColumnsEnum.NID_PASSPORT;
        final var deleted = PersonColumnsEnum.DELETED;

        assertEquals("ID_USER", idUser.getColumnName());
        assertEquals("METADATA", metadata.getColumnName());
        assertEquals("NAME", name.getColumnName());
        assertEquals("NID_PASSPORT", nidPassport.getColumnName());
        assertEquals("DELETED", deleted.getColumnName());
    }

    @Test
    void personColumnsEnum_ValuesArray_ThenContainsAllEnums() {
        final var values = PersonColumnsEnum.values();

        assertArrayEquals(new PersonColumnsEnum[]{
                PersonColumnsEnum.ID_USER,
                PersonColumnsEnum.METADATA,
                PersonColumnsEnum.NAME,
                PersonColumnsEnum.NID_PASSPORT,
                PersonColumnsEnum.DELETED
        }, values);
    }
}
