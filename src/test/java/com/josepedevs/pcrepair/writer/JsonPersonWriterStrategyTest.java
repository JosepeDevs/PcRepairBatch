package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonPersonWriterStrategyTest {

    @Mock
    private FolderCreator folderCreator;

    @Mock
    private FileSystemResource resource;

    @Mock
    private AppPropertiesReader props;

    private JsonPersonWriterStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new JsonPersonWriterStrategy(folderCreator);
    }

    @Test
    void createWriter_GivenValidProps_ThenWriterIsConfigured() {
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);

        final var writer = strategy.createWriter(props);

        assertThat(writer).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    void aggregate_GivenPerson_ThenReturnsValidJson() {
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);
        final var writer = strategy.createWriter(props);

        final var aggregator = ReflectionTestUtils.getField(writer, "lineAggregator");

        assertThat(aggregator).isInstanceOf(LineAggregator.class);

        final var person = Person.builder()
                .idUser("1")
                .name("Josepe")
                .metadata("meta")
                .nidPassport("XYZ")
                .deleted(0)
                .build();

        Assertions.assertNotNull(aggregator);
        final String json = ((LineAggregator<Person>) aggregator).aggregate(person);

        assertThat(json)
                .contains("\"idUser\":\"1\"")
                .contains("\"name\":\"Josepe\"")
                .contains("\"metadata\":\"meta\"");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aggregate_GivenMultiplePersons_ThenCommaSeparated() {
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);
        final var writer = strategy.createWriter(props);

        final var aggregator = ReflectionTestUtils.getField(writer, "lineAggregator");
        assertThat(aggregator).isInstanceOf(LineAggregator.class);

        final var p1 = Person.builder().idUser("1").build();
        final var p2 = Person.builder().idUser("2").build();

        Assertions.assertNotNull(aggregator);
        final var first = ((LineAggregator<Person>) aggregator).aggregate(p1);
        final var second = ((LineAggregator<Person>) aggregator).aggregate(p2);

        assertThat(first).startsWith("{");
        assertThat(second).startsWith(",{"); // comma prepended for second element
    }
}