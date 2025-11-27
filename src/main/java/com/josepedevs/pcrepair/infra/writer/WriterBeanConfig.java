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
            @Value("#{jobParameters['exportFormat']}") String exportFormat
    ) {

        final var props = AppPropertiesReader.builder()
                .outputFile(outputFileName)
                .delimiter(delimiter)
                .includeHeaders(Boolean.parseBoolean(includeHeaders))
                .exportFormat(exportFormat)
                .build();
        final var strategy = writerFactory.getStrategy(props.getExportFormat());
        FlatFileItemWriter<Person> writer = strategy.createWriter(props);
        final var resource = folderCreator.createOutputResourceIfNotExists(props);
        writer.setResource(resource);
        return writer;
    }
}