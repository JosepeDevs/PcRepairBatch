package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.domain.interfaces.CsvFileWriterStrategy;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvHeaderTaskletFactoryTest {

    @Mock
    private FolderCreator folderCreator;

    @Mock
    private CsvFileWriterStrategy fileWriterStrategy;

    @Mock
    private AppPropertiesReader props;

    @Test
    void create_GivenProps_ThenTaskletWritesHeaders() throws Exception {
        final var outputFile = File.createTempFile("test", ".csv");
        outputFile.deleteOnExit();

        when(folderCreator.createOutputResourceIfNotExists(props))
                .thenReturn(new org.springframework.core.io.FileSystemResource(outputFile));

        when(props.getDelimiter()).thenReturn(",");

        final var factory = new CsvHeaderTaskletFactory(folderCreator, fileWriterStrategy);
        final var tasklet = factory.create(props);

        tasklet.execute(null, null);

        final var expectedHeader = Stream.of(PersonColumnsEnum.values())
                .map(PersonColumnsEnum::getColumnName)
                .collect(Collectors.joining(","));

        verify(fileWriterStrategy).writeFile(outputFile, expectedHeader);
    }

    @Test
    void create_GivenIOExceptionInWriter_ThenThrowsRuntimeException() throws Exception {
        final var outputFile = File.createTempFile("test", ".csv");
        outputFile.deleteOnExit();

        when(folderCreator.createOutputResourceIfNotExists(props))
                .thenReturn(new org.springframework.core.io.FileSystemResource(outputFile));

        when(props.getDelimiter()).thenReturn(",");

        doThrow(IOException.class).when(fileWriterStrategy).writeFile(Mockito.any(), Mockito.any());

        final var factory = new CsvHeaderTaskletFactory(folderCreator, fileWriterStrategy);
        final var tasklet = factory.create(props);

        assertThrows(IOException.class, () -> tasklet.execute(null, null));
    }
}