package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LineAggregatorFactory {

    private final FieldExtractor fieldExtractor;

    public LineAggregator<Person> createLineAggregator(AppPropertiesReader props) {
        final var extractor = new BeanWrapperFieldExtractor<Person>();
        extractor.setNames(fieldExtractor.extractFieldNames(Person.class));

        final var aggregator = new DelimitedLineAggregator<Person>();
        aggregator.setDelimiter(props.getDelimiter());
        aggregator.setFieldExtractor(extractor);

        return aggregator;
    }
}
