package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.application.util.FolderCreator;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WriterBeanConfigTest {

    @Mock
    private PersonWriterFactory writerFactory;

    @Mock
    private FolderCreator folderCreator;

    @Mock
    private AppPropertiesReader props;

    @Mock
    private PersonWriterStrategy strategy;
    
    @InjectMocks
    private WriterBeanConfig writerBeanConfig;

    @BeforeEach
    void setUp() {
        writerBeanConfig = new WriterBeanConfig(writerFactory, folderCreator);
    }

    @Test
    void personWriter_GivenCsvFormat_ReturnsWriterWithResource() {

        when(props.getExportFormat()).thenReturn("CSV");
        when(writerFactory.getStrategy("CSV")).thenReturn(strategy);

        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
        FileSystemResource resource = new FileSystemResource("target/test.csv");

        when(strategy.createWriter(props)).thenReturn(writer);
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);

        FlatFileItemWriter<Person> result = writerBeanConfig.personWriter(props);

        assertSame(writer, result, "Returned writer should be the one from strategy");

        verify(writerFactory).getStrategy("CSV");
        verify(strategy).createWriter(props);
        verify(folderCreator).createOutputResourceIfNotExists(props);
    }

    @Test
    void personWriter_GivenJsonFormat_ReturnsWriterWithResource(){

        when(props.getExportFormat()).thenReturn("JSON");
        when(writerFactory.getStrategy("JSON")).thenReturn(strategy);

        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
        FileSystemResource resource = new FileSystemResource("target/test.json");

        when(strategy.createWriter(props)).thenReturn(writer);
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);

        FlatFileItemWriter<Person> result = writerBeanConfig.personWriter(props);

        assertSame(writer, result);

        verify(writerFactory).getStrategy("JSON");
        verify(strategy).createWriter(props);
        verify(folderCreator).createOutputResourceIfNotExists(props);
    }
}
