package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.interfaces.OutputResourceStrategy;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PersonWriterFactory {

    private final OutputResourceStrategy resourceStrategy;
    private final LineAggregatorFactory aggregatorFactory;

    //TODO añadir writer de Json

    public FlatFileItemWriter<Person> createWriter(AppPropertiesReader props) {
        final var writer = new FlatFileItemWriter<Person>();
        writer.setResource(resourceStrategy.createOutputResource(props));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(aggregatorFactory.createLineAggregator(props));
        return writer;
    }
}
