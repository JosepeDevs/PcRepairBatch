package com.josepedevs.pcrepair.domain.interfaces;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.springframework.batch.item.file.FlatFileItemWriter;

public interface PersonWriterStrategy {
    FlatFileItemWriter<Person> createWriter(AppPropertiesReader props);
}

