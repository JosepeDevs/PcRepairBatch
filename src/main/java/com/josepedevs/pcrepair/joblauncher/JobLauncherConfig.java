package com.josepedevs.pcrepair.joblauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobLauncherConfig {

    @Bean
    public CommandLineRunner runJob(JobLauncher jobLauncherConfig, Job exportPersonsJob) {
        return args -> {
            jobLauncherConfig.run(exportPersonsJob,
                    new JobParametersBuilder()
                            .addLong("timestamp", System.currentTimeMillis())
                            .toJobParameters());
        };
    }
}