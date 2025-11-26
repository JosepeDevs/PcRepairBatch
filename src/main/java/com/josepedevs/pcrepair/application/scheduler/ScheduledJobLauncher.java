package com.josepedevs.pcrepair.application.scheduler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job exportPersonsJob;

    @Value("${batch.export.cron}")
    @Getter
    @Setter
    private String cron;

    @Scheduled(cron = "${batch.export.cron}")
    public void runScheduled() {
        runJob();
    }

    public void runJob() {
        try{
            JobParameters params = new JobParametersBuilder()
                    .addString("run.id", UUID.randomUUID() + "_" + System.currentTimeMillis(), true)
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            log.info("executing the job with cron {}", cron);
            jobLauncher.run(exportPersonsJob, params);
        } catch (Exception e) {
            log.error("Could not execute scheduled job, more info: {}", e.getLocalizedMessage());
        }
    }
}