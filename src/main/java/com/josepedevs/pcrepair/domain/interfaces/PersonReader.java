package com.josepedevs.pcrepair.domain.interfaces;

import com.josepedevs.pcrepair.domain.model.Person;

import java.util.stream.Stream;

public interface PersonReader {
    Stream<Person> readAll();
}
