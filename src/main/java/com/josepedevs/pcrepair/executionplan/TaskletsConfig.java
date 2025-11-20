package com.josepedevs.pcrepair.executionplan;

import com.josepedevs.pcrepair.listener.JobCompletionLoggingListener;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.tasklets.LogPropertiesTasklet;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TaskletsConfig {

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
