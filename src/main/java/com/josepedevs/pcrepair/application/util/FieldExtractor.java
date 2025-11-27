package com.josepedevs.pcrepair.application.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import org.springframework.stereotype.Service;

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
