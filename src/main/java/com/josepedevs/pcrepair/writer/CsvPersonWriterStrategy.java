package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.interfaces.PersonWriterStrategy;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import com.josepedevs.pcrepair.util.FolderCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.stereotype.Component;

@Component("csvPersonWriterStrategy")
@RequiredArgsConstructor
public class CsvPersonWriterStrategy implements PersonWriterStrategy {

    private final FieldExtractor fieldExtractor;
    private final FolderCreator folderCreator;
    private final PersonCsvHeaderCallback headerCallback;

    @Override
    public FlatFileItemWriter<Person> createWriter(AppPropertiesReader props) {

        final var writer = new FlatFileItemWriter<Person>();
        writer.setResource(folderCreator.createOutputResourceIfNotExists(props));
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

        return writer;
    }
}


