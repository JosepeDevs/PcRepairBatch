package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExportPersonAdapterTest {

    @Test
    void init_GivenMocks_ThenWriterCreatedAndOpened() {
        final FlatFileItemWriter<Person> writer = mock(FlatFileItemWriter.class);
        final var factory = mock(PersonWriterFactory.class);
        final var props = mock(AppPropertiesReader.class);
        when(props.getExportFormat()).thenReturn("CSV");
        when(factory.getStrategy("CSV")).thenReturn(strategy -> writer);

        final var adapter = new ExportPersonAdapter(factory, props);

        adapter.init();

        assertNotNull(adapter);
        verify(factory).getStrategy("CSV");
        verify(writer).open(any());
    }

    @Test
    void export_GivenListOfPersons_ThenWriterWritesEachPerson() throws Exception {
        final FlatFileItemWriter<Person> writer = mock(FlatFileItemWriter.class);
        final var factory = mock(PersonWriterFactory.class);
        final var props = mock(AppPropertiesReader.class);
        when(props.getExportFormat()).thenReturn("CSV");
        when(factory.getStrategy("CSV")).thenReturn(strategy -> writer);

        final var adapter = new ExportPersonAdapter(factory, props);
        adapter.init();

        final var person1 = Person.builder().build();
        final var person2 = Person.builder().build();

        adapter.export(List.of(person1, person2));

        verify(writer, times(2)).write(any(Chunk.class));
        verify(writer).close();
    }

    @Test
    void export_WhenWriterThrowsException_ThenCloseStillCalled() throws Exception {
        final FlatFileItemWriter<Person> writer = mock(FlatFileItemWriter.class);
        doThrow(new RuntimeException("write error")).when(writer).write(any());
        final var factory = mock(PersonWriterFactory.class);
        final var props = mock(AppPropertiesReader.class);
        when(props.getExportFormat()).thenReturn("CSV");
        when(factory.getStrategy("CSV")).thenReturn(strategy -> writer);

        final var adapter = new ExportPersonAdapter(factory, props);
        adapter.init();

        final var person = Person.builder().build();
        adapter.export(List.of(person));

        verify(writer).close();
    }
}
