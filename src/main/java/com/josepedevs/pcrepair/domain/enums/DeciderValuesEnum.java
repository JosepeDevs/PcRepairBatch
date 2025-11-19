package com.josepedevs.pcrepair.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeciderValuesEnum {

    INCLUDE_HEADERS("INCLUDE_HEADERS"),
    SKIP_HEADERS("SKIP_HEADERS");

    private final String deciderValue;
}
