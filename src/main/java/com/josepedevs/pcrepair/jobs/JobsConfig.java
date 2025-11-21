package com.josepedevs.pcrepair.jobs;

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
                                JobExecutionListener jobCompletionListener) {

        return new JobBuilder(JobAndStepValuesEnum.JOB_NAME.getValue(), jobRepository)
                .start(logPropertiesStep)
                .next(exportPersonsStep)
                .listener(jobCompletionListener)
                .build();
    }
}

