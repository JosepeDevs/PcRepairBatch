package com.josepedevs.pcrepair.infra.writer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josepedevs.pcrepair.application.util.FolderCreator;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

@ExtendWith(MockitoExtension.class)
class WriterBeanConfigTest {

    @Mock
    private PersonWriterFactory writerFactory;

    @Mock
    private FolderCreator folderCreator;

    @Mock
    private PersonWriterStrategy strategy;

    @Mock
    private FlatFileItemWriter<Person> writer;

    @Mock
    private FileSystemResource resource;

    @InjectMocks
    private WriterBeanConfig config;

    @Test
    void personWriter_GivenValidParameters_ThenReturnsConfiguredWriter() {
        final var outputFileName = "people.csv";
        final var delimiter = ";";
        final var includeHeaders = "true";
        final var exportFormat = "csv";

        when(writerFactory.getStrategy(exportFormat)).thenReturn(strategy);
        final var propsCaptor = ArgumentCaptor.forClass(AppPropertiesReader.class);
        when(strategy.createWriter(any(AppPropertiesReader.class))).thenReturn(writer);
        when(folderCreator.createOutputResourceIfNotExists(any(AppPropertiesReader.class)))
                .thenReturn(resource);

        final var result = config.personWriter(outputFileName, delimiter, includeHeaders, exportFormat);

        assertAll(
                () -> assertSame(writer, result),
                () -> verify(writerFactory).getStrategy(exportFormat),
                () -> verify(strategy).createWriter(propsCaptor.capture()),
                () -> verify(folderCreator).createOutputResourceIfNotExists(any(AppPropertiesReader.class)),
                () -> verify(writer).setResource(resource),
                () -> assertEquals(outputFileName, propsCaptor.getValue().getOutputFile()),
                () -> assertEquals(delimiter, propsCaptor.getValue().getDelimiter()),
                () -> assertTrue(propsCaptor.getValue().isIncludeHeaders()),
                () -> assertEquals(exportFormat, propsCaptor.getValue().getExportFormat()));
    }
}
