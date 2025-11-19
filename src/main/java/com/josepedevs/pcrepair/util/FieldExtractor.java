package com.josepedevs.pcrepair.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class FieldExtractor {
    public String[] extractFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .map(Field::getName)
                .toArray(String[]::new);
    }

    public String extractFieldNamesIntoString(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }
}
