package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@AllArgsConstructor
@Slf4j
public class LogPropertiesTasklet implements Tasklet {

    private final AppPropertiesReader appPropertiesReader;

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) {
        log.info("Export Properties ↓");
        log.info("Output Directory: {}", appPropertiesReader.getOutputDirectory());
        log.info("Output File: {}", appPropertiesReader.getOutputFile());
        log.info("Delimiter: {}", appPropertiesReader.getDelimiter());
        log.info("Include headers: {}", appPropertiesReader.isIncludeHeaders());
        return RepeatStatus.FINISHED;
    }
}
