package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PersonCsvWriterConfig {

    private final PersonWriterFactory writerFactory;

    @Bean("personCsvFileWriter")
    public FlatFileItemWriter<Person> personCsvWriter(AppPropertiesReader props) {
        log.info("Writing person values...");
        return writerFactory.createWriter(props);
    }

}

