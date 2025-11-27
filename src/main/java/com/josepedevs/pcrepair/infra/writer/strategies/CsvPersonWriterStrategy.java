package com.josepedevs.pcrepair.infra.writer.strategies;

import com.josepedevs.pcrepair.application.util.FieldExtractor;
import com.josepedevs.pcrepair.application.util.FolderCreator;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.PersonCsvHeaderCallback;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.stereotype.Component;

@Component("csvPersonWriterStrategy")
@RequiredArgsConstructor
@Slf4j
public class CsvPersonWriterStrategy implements PersonWriterStrategy {

    private final FieldExtractor fieldExtractor;
    private final FolderCreator folderCreator;
    private final PersonCsvHeaderCallback headerCallback;

    @Override
    public FlatFileItemWriter<Person> createWriter(AppPropertiesReader props) {

        final var writer = new FlatFileItemWriter<Person>();
        final var resource = folderCreator.createOutputResourceIfNotExists(props);
        writer.setResource(resource);
        writer.setAppendAllowed(false);

        if (props.isIncludeHeaders()) {
            writer.setHeaderCallback(headerCallback);
        }

        final var aggregator = new DelimitedLineAggregator<Person>();
        aggregator.setDelimiter(props.getDelimiter());

        final var extractor = new BeanWrapperFieldExtractor<Person>();
        extractor.setNames(fieldExtractor.extractFieldNames(Person.class));
        aggregator.setFieldExtractor(extractor);

        writer.setLineAggregator(aggregator);
        log.info("Writing CSV to {}", resource.getFile().getAbsolutePath());

        return writer;
    }
}
