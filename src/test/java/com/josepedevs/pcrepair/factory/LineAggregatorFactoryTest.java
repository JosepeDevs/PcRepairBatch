package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.transform.LineAggregator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LineAggregatorFactoryTest {

    @Test
    void createLineAggregator_GivenProps_ThenAggregatorConfigured() {
        var fieldExtractor = mock(FieldExtractor.class);
        var props = mock(AppPropertiesReader.class);

       when(fieldExtractor.extractFieldNames(Person.class))
                .thenReturn(new String[]{"idUser", "name"});

       when(props.getDelimiter()).thenReturn(";");

        var factory = new LineAggregatorFactory(fieldExtractor);
        LineAggregator<Person> aggregator = factory.createLineAggregator(props);

        Person person = Person.builder()
                .idUser("1")
                .name("Pepe")
                .build();

        String line = aggregator.aggregate(person);

        assertEquals("1;Pepe", line);
    }
}