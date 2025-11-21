package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PersonWriterConfig {

    private final PersonWriterFactory writerFactory;

    @Bean("personFileWriter")
    public FlatFileItemWriter<Person> personWriter(AppPropertiesReader props) {
        if(Objects.isNull(props.getExportFormat())) {
            throw new IllegalStateException("No format specified in application.properties and/or no default value included.");
        }
        final var strategy = writerFactory.getStrategy(props.getExportFormat());
        return strategy.createWriter(props);
    }

}

