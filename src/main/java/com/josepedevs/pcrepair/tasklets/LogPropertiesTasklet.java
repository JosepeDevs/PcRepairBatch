package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.propertyreader.AppPropetiesReader;
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

    private final AppPropetiesReader appPropetiesReader;

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) {
        log.info("Export Properties ↓");
        log.info("Output Directory: {}", appPropetiesReader.getOutputDirectory());
        log.info("Output File: {}", appPropetiesReader.getOutputFile());
        log.info("Delimiter: {}", appPropetiesReader.getDelimiter());
        log.info("Include headers: {}", appPropetiesReader.isIncludeHeaders());
        return RepeatStatus.FINISHED;
    }
}
