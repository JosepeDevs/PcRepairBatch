package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.Person;
import com.josepedevs.pcrepair.propertyreader.PropertyReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@AllArgsConstructor
@Configuration
public class PersonCsvWriterConfig {

    private final FieldExtractor fieldExtractor;

    @Bean("personCsvFileWriter")
    public FlatFileItemWriter<Person> personCsvWriter(PropertyReader props) {
        final var writer = new FlatFileItemWriter<Person>();
        writer.setResource(createOutputResourceIfNotExists(props));
        writer.setAppendAllowed(false);

        final var aggregator = new DelimitedLineAggregator<Person>();
        aggregator.setDelimiter(props.getDelimiter());

        final var extractor = new BeanWrapperFieldExtractor<Person>();
        extractor.setNames(fieldExtractor.extractFieldNames(Person.class));
        aggregator.setFieldExtractor(extractor);

        writer.setLineAggregator(aggregator);

        return writer;
    }

    private FileSystemResource createOutputResourceIfNotExists(PropertyReader props) {
        final var dir = Paths.get(props.getOutputDirectory());
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            final var msg = String.format("Could not create output directory: %s" , dir);
            log.error(msg);
            throw new IllegalStateException(msg, e);
        }

        return new FileSystemResource(dir.resolve(props.getOutputFile()).toFile());
    }

}

