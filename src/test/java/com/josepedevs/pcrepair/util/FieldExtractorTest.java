package com.josepedevs.pcrepair.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldExtractorTest {

    @Test
    void extractFieldNames_GivenClassWithFields_ThenReturnsFieldNames() {
        final var extractor = new FieldExtractor();

        final var result = extractor.extractFieldNames(DummyClass.class);

        assertArrayEquals(new String[]{"id", "name"}, result);
    }


    @Test
    void extractFieldNames_GivenClassWithSyntheticFields_ThenIgnoresSyntheticFields() {
        final var extractor = new FieldExtractor();

        final var result = extractor.extractFieldNames(OuterClass.InnerClass.class);

        assertArrayEquals(new String[]{"realField"}, result);
    }

    @Test
    void extractFieldNames_GivenNullClass_ThenThrowsIllegalArgumentException() {
        final var extractor = new FieldExtractor();

        final var exception = assertThrows(IllegalArgumentException.class,
                () -> extractor.extractFieldNames(null));

        assertEquals("Class must not be null", exception.getMessage());
    }
    @Data
    private static class DummyClass {
        private int id;
        private String name;
    }

    @Data
    static class OuterClass {
        private String outerField;

        // Non-static inner class -> compiler will generate synthetic "this$0" field
        @Data
        class InnerClass {
            private String realField;
        }
    }
}