package com.josepedevs.pcrepair.logtasklet;

import com.josepedevs.pcrepair.propertyreader.PropertyReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@AllArgsConstructor
@Slf4j
public class LogPropertiesTasklet implements Tasklet {

    private final PropertyReader propertyReader;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("==== Export Properties ====");
        log.info("Output Directory: {}", propertyReader.getOutputDirectory());
        log.info("Output File: {}", propertyReader.getOutputFile());
        log.info("Delimiter: {}", propertyReader.getDelimiter());
        log.info("===========================");
        return RepeatStatus.FINISHED;
    }
}
