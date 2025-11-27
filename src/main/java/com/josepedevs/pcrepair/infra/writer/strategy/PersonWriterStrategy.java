package com.josepedevs.pcrepair.infra.writer.strategy;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import org.springframework.batch.item.file.FlatFileItemWriter;

public interface PersonWriterStrategy {
    FlatFileItemWriter<Person> createWriter(AppPropertiesReader props);
}
