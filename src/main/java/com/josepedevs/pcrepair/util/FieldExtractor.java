package com.josepedevs.pcrepair.util;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

@Service
public class FieldExtractor {

    public String[] extractFieldNames(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
