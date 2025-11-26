package com.josepedevs.pcrepair.application.joblauncher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
public class JobLauncherConfig {

    @Bean
    public CommandLineRunner runJob(JobLauncher jobLauncherConfig, Job exportPersonsJob) {
        return args -> {
            try {
                jobLauncherConfig.run(exportPersonsJob,
                        new JobParametersBuilder()
                                .addString("run.id", UUID.randomUUID() + "_" + System.currentTimeMillis(), true)
                                .addLong("timestamp", System.currentTimeMillis())
                                .toJobParameters());
            } catch (Exception e) {
                log.error("Failed to run job", e);
            }
        };
    }
}
