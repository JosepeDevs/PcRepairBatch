package com.josepedevs.pcrepair.jobs;

import com.josepedevs.pcrepair.listener.JobCompletionLoggingListener;
import com.josepedevs.pcrepair.propertyreader.AppPropetiesReader;
import com.josepedevs.pcrepair.tasklets.LogPropertiesTasklet;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TaskletsConfig {

    private final AppPropetiesReader appPropetiesReader;

    @Bean
    public Tasklet logPropertiesTasklet() {
        return new LogPropertiesTasklet(appPropetiesReader);
    }

    @Bean
    public JobExecutionListener jobCompletionListener() {
        return new JobCompletionLoggingListener();
    }

}
