package com.josepedevs.pcrepair.executionplan;

import com.josepedevs.pcrepair.decider.IncludeHeadersDecider;
import com.josepedevs.pcrepair.domain.enums.DeciderValuesEnum;
import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobsConfig {

    @Bean
    public Job exportPersonsJob(JobRepository jobRepository,
                                Step logPropertiesStep,
                                Step exportPersonsStep,
                                Step writeHeadersStep,
                                IncludeHeadersDecider decider,
                                JobExecutionListener jobCompletionListener) {

        return new JobBuilder(JobAndStepValuesEnum.JOB_NAME.getValue(), jobRepository)
                .start(logPropertiesStep)
                .next(decider)
                .from(decider)
                    .on(DeciderValuesEnum.INCLUDE_HEADERS.getDeciderValue())
                    .to(writeHeadersStep)
                    .next(exportPersonsStep)
                .from(decider)
                    .on(DeciderValuesEnum.SKIP_HEADERS.getDeciderValue())
                    .to(exportPersonsStep)
                .from(decider)
                    .on("*") // unexpected decider statuses
                    .fail()
                .end()
                .listener(jobCompletionListener)
                .build();
    }

}
