package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.application.util.FolderCreator;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class WriterBeanConfig {

    private final PersonWriterFactory writerFactory;
    private final FolderCreator folderCreator;

    @Bean
    @StepScope
    public FlatFileItemWriter<Person> personWriter(
            @Value("#{jobParameters['outputFileName']}") String outputFileName,
            @Value("#{jobParameters['delimiter']}") String delimiter,
            @Value("#{jobParameters['includeHeaders']}") String includeHeaders,
            @Value("#{jobParameters['exportFormat']}") String exportFormat) {

        final var builder = AppPropertiesReader.builder();

        // Job parameters are null for scheduled runs; set values only when non-null because Lombok @Builder.Default is ignored if explicitly set to null
        setIfNotNull(builder::outputFile, outputFileName);
        setIfNotNull(builder::delimiter, delimiter);
        setIfNotNull(builder::exportFormat, exportFormat);
        setIfNotNull(includeHeaders != null ? v -> builder.includeHeaders(Boolean.parseBoolean(v)) : null, includeHeaders);

        final var props = builder.build();
        final var strategy = writerFactory.getStrategy(props.getExportFormat());
        FlatFileItemWriter<Person> writer = strategy.createWriter(props);
        final var resource = folderCreator.createOutputResourceIfNotExists(props);
        writer.setResource(resource);
        return writer;
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (!Objects.isNull(value)) {
            setter.accept(value);
        }
    }
}
