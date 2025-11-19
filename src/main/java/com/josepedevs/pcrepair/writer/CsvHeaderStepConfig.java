package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.propertyreader.AppPropetiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class CsvHeaderStepConfig {

    @Bean
    public Step writeHeadersStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 AppPropetiesReader props,
                                 FolderCreator folderCreator) {

        Tasklet tasklet = (contribution, chunkContext) -> {
            File outputFile = folderCreator.createOutputResourceIfNotExists(props).getFile();

            try (FileWriter writer = new FileWriter(outputFile, false)) {
                final String headerLine = Stream.of(PersonColumnsEnum.values())
                        .map(PersonColumnsEnum::getColumnName)
                        .collect(Collectors.joining(props.getDelimiter()));

                log.info("Writing headers to {}", outputFile.getAbsolutePath());
                writer.write(headerLine + "\n");
            } catch (IOException e) {
                throw new RuntimeException("Failed to write CSV headers", e);
            }

            return RepeatStatus.FINISHED;
        };

        return new StepBuilder(JobAndStepValuesEnum.WRITE_HEADERS_STEP.getValue(), jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}
