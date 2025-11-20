package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCsvWriterConfigTest {

    @Mock
    private PersonWriterFactory writerFactory;

    @Test
    void personCsvWriter_GivenProps_ThenWriterReturned() {
        final var props = mock(AppPropertiesReader.class);
        FlatFileItemWriter<Person> expectedWriter =mock(FlatFileItemWriter.class);

       when(writerFactory.createWriter(props)).thenReturn(expectedWriter);

        final var config = new PersonCsvWriterConfig(writerFactory);

        final var result = config.personCsvWriter(props);

        assertEquals(expectedWriter, result);
    }
}