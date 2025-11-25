package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PersonFileWriter {

    private final PersonWriterFactory writerFactory;

    public FlatFileItemWriter<Person> personWriter(AppPropertiesReader props) {
        if(Objects.isNull(props.getExportFormat())) {
            throw new IllegalStateException("No format specified in application.properties and/or no default value included.");
        }
        final var strategy = writerFactory.getStrategy(props.getExportFormat());
        return strategy.createWriter(props);
    }

}

