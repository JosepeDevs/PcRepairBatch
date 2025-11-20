package com.josepedevs.pcrepair.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum FileFormatsEnum {

    CSV("csv"),
    JSON("json");

    private final String format;

    public static FileFormatsEnum from(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("File format cannot be null");
        }

        return Arrays.stream(values())
                .filter(f -> f.format.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unsupported export format: " + value));
    }
}
