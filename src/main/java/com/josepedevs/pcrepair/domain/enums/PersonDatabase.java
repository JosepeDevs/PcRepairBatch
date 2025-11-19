package com.josepedevs.pcrepair.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonDatabase {

    private static final String DATABASE_NAME = "persons";
    private final PersonColumnsEnum personColumnsEnum;

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
}

