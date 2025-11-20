package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.interfaces.OutputResourceStrategy;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonWriterFactoryTest {
    
    @Test
    void createWriter_WritesPersonToFile(@TempDir File tempDir) throws Exception {
        final var props = mock(AppPropertiesReader.class);
        final var resource = new FileSystemResource(new File(tempDir, "persons.txt"));
        final var aggregator = (LineAggregator<Person>) person -> person.getIdUser() + ";" + person.getName();

        final var resourceStrategy =mock(OutputResourceStrategy.class);
        when(resourceStrategy.createOutputResource(props)).thenReturn(resource);

        final var aggregatorFactory =mock(LineAggregatorFactory.class);
        when(aggregatorFactory.createLineAggregator(props)).thenReturn(aggregator);

        final var factory = new PersonWriterFactory(resourceStrategy, aggregatorFactory);
        final var writer = factory.createWriter(props);

        writer.setResource(resource);
        writer.afterPropertiesSet();

        verify(resourceStrategy).createOutputResource(props);
        verify(aggregatorFactory).createLineAggregator(props);

        final var person = Person.builder()
                .idUser("1")
                .name("Alice")
                .build();

        final var chunk = new Chunk<>(List.of(person));

        try {
            writer.open(new ExecutionContext());
            writer.write(chunk);
        } finally {
            writer.close();
        }

        final var content = Files.readString(resource.getFile().toPath());
        assertTrue(content.contains("1;Alice"));
    }
}