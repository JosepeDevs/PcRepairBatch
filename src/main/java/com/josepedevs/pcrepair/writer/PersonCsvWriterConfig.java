package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropetiesReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import com.josepedevs.pcrepair.util.FolderCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@AllArgsConstructor
@Configuration
public class PersonCsvWriterConfig {

    private final FieldExtractor fieldExtractor;
    private final FolderCreator folderCreator;

    @Bean("personCsvFileWriter")
    public FlatFileItemWriter<Person> personCsvWriter(AppPropetiesReader props) {
        log.info("Writing person values...");
        final var writer = new FlatFileItemWriter<Person>();
        writer.setResource(folderCreator.createOutputResourceIfNotExists(props));
        writer.setAppendAllowed(true);

        final var aggregator = new DelimitedLineAggregator<Person>();
        aggregator.setDelimiter(props.getDelimiter());

        final var extractor = new BeanWrapperFieldExtractor<Person>();
        extractor.setNames(fieldExtractor.extractFieldNames(Person.class));
        aggregator.setFieldExtractor(extractor);

        writer.setLineAggregator(aggregator);

        return writer;
    }

}

