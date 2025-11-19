package com.josepedevs.pcrepair.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.lang.NonNull;

@Slf4j
public class JobCompletionLoggingListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Starting job.");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions().forEach(se -> {
            log.info("{} - {}", se.getStepName(), se.getStatus());
            se.getFailureExceptions().forEach(Throwable::printStackTrace);
        });

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished successfully.");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("Job failed.");
        }
    }
}

