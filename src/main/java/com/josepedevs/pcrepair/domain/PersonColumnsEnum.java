package com.josepedevs.pcrepair.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonColumnsEnum {

    ID_USER("ID_USER"),
    METADATA("METADATA"),
    NAME("NAME"),
    NID_PASSPORT("NID_PASSPORT"),
    DELETED("DELETED");

    private final String columnName;

}

