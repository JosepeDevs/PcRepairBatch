package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemWriter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonWriterPortConfigTest {

    @Mock
    private AppPropertiesReader props;

    @Mock
    private PersonWriterFactory factory;

    @Mock
    private PersonWriterStrategy strategy;

    @Mock
    private FlatFileItemWriter<Person> writer;

    private PersonFileWriter config;

    @BeforeEach
    void setup() {
        config = new PersonFileWriter(factory);
    }

    @Test
    void personWriter_GivenFormat_ThenReturnsWriter() {
        when(props.getExportFormat()).thenReturn("csv");
        when(factory.getStrategy("csv")).thenReturn(strategy);
        when(strategy.createWriter(props)).thenReturn(writer);

        final var result = config.personWriter(props);

        assertThat(result).isSameAs(writer);
    }
}
