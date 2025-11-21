package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FieldExtractor;
import com.josepedevs.pcrepair.util.FolderCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvPersonWriterStrategyTest {

    @Mock
    private FieldExtractor extractor;

    @Mock
    private FolderCreator folderCreator;

    @Mock
    private PersonCsvHeaderCallback headerCallback;

    @Mock
    private AppPropertiesReader props;

    @Mock
    private FileSystemResource resource;

    private CsvPersonWriterStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new CsvPersonWriterStrategy(extractor, folderCreator, headerCallback);
    }

    @Test
    void createWriter_GivenIncludeHeaders_ThenHeaderIsSet() {
        when(props.isIncludeHeaders()).thenReturn(true);
        when(props.getDelimiter()).thenReturn(",");
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);
        when(extractor.extractFieldNames(Person.class)).thenReturn(new String[]{"idUser", "name"});

        final var writer = strategy.createWriter(props);

        // Use reflection to get private headerCallback
        final var header = ReflectionTestUtils.getField(writer, "headerCallback");
        assertThat(header).isEqualTo(headerCallback);
    }

    @Test
    void createWriter_GivenProps_ThenAggregatorConfigured() {
        when(props.isIncludeHeaders()).thenReturn(false);
        when(props.getDelimiter()).thenReturn(";");
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);
        when(extractor.extractFieldNames(Person.class)).thenReturn(new String[]{"idUser", "name"});

        final var writer = strategy.createWriter(props);

        final var aggregator = ReflectionTestUtils.getField(writer, "lineAggregator");
        assertThat(aggregator).isInstanceOf(DelimitedLineAggregator.class);

        Assertions.assertNotNull(aggregator);
        final var delimiter = ReflectionTestUtils.getField(aggregator, "delimiter");
        assertThat(delimiter).isEqualTo(";");
    }

}
