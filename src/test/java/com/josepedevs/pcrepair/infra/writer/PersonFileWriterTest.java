package com.josepedevs.pcrepair.infra.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.file.FlatFileItemWriter;

class PersonFileWriterTest {

    @Mock
    private PersonWriterFactory writerFactory;

    @Mock
    private PersonWriterStrategy csvStrategy;

    @Mock
    private PersonWriterStrategy jsonStrategy;

    @Mock
    private AppPropertiesReader props;

    private PersonFileWriter personFileWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personFileWriter = new PersonFileWriter(writerFactory);
    }

    @Test
    void personWriter_GivenCsvFormat_ThenReturnsCsvWriter() {
        when(props.getExportFormat()).thenReturn("CSV");
        when(writerFactory.getStrategy("CSV")).thenReturn(csvStrategy);
        final var writer = mock(FlatFileItemWriter.class);
        when(csvStrategy.createWriter(props)).thenReturn(writer);

        final var result = personFileWriter.personWriter(props);

        assertSame(writer, result);
        verify(writerFactory).getStrategy("CSV");
        verify(csvStrategy).createWriter(props);
    }

    @Test
    void personWriter_GivenJsonFormat_ThenReturnsJsonWriter() {
        when(props.getExportFormat()).thenReturn("JSON");
        when(writerFactory.getStrategy("JSON")).thenReturn(jsonStrategy);
        final var writer = mock(FlatFileItemWriter.class);
        when(jsonStrategy.createWriter(props)).thenReturn(writer);

        final var result = personFileWriter.personWriter(props);

        assertSame(writer, result);
        verify(writerFactory).getStrategy("JSON");
        verify(jsonStrategy).createWriter(props);
    }

    @Test
    void personWriter_GivenNullFormat_ThenThrowsIllegalStateException() {
        when(props.getExportFormat()).thenReturn(null);

        final var exception = assertThrows(IllegalStateException.class, () -> personFileWriter.personWriter(props));

        assertEquals(
                "No format specified in application.properties and/or no default value included.",
                exception.getMessage());
        verifyNoInteractions(writerFactory);
    }
}
