package com.josepedevs.pcrepair.application.tasklet;

import com.josepedevs.pcrepair.application.listener.JobCompletionLoggingListener;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TaskletConfig {

    private final AppPropertiesReader appPropertiesReader;

    @Bean
    public Tasklet logPropertiesTasklet() {
        return new LogPropertiesTasklet(appPropertiesReader);
    }

    @Bean
    public JobExecutionListener jobCompletionListener() {
        return new JobCompletionLoggingListener();
    }

}
