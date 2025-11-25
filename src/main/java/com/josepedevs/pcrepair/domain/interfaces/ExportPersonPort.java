package com.josepedevs.pcrepair.domain.interfaces;

import com.josepedevs.pcrepair.domain.model.Person;

import java.util.List;

public interface ExportPersonPort {
    void export(List<Person> people);
}
