package com.josepedevs.pcrepair.joblauncher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JobLauncherConfig {

    @Bean
    public CommandLineRunner runJob(JobLauncher jobLauncherConfig, Job exportPersonsJob) {
        return args -> {
            try {
                jobLauncherConfig.run(exportPersonsJob,
                        new JobParametersBuilder()
                                .addLong("timestamp", System.currentTimeMillis())
                                .toJobParameters());
            } catch (Exception e) {
                log.error("Failed to run job", e);
            }
        };
    }
}
