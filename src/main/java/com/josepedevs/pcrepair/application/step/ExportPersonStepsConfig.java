package com.josepedevs.pcrepair.application.step;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.domain.interfaces.ExportPersonPort;
import com.josepedevs.pcrepair.domain.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;

@AllArgsConstructor
@Configuration
public class ExportPersonStepsConfig {

    private final AppPropertiesReader appPropertiesReader;

    @Bean
    public Step logPropertiesStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  Tasklet logPropertiesTasklet) {
        return new StepBuilder(JobAndStepValuesEnum.LOG_PROPERTIES_STEP.getValue(), jobRepository)
                .tasklet(logPropertiesTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step exportPersonsStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  ItemReader<Person> reader,
                                  ExportPersonPort personExporter) {

        return new StepBuilder(JobAndStepValuesEnum.EXPORT_PERSON_STEP.getValue(), jobRepository)
                .<Person, Person>chunk(appPropertiesReader.getChunkSize(), transactionManager)
                .reader(reader)
                .writer(items -> personExporter.export(new ArrayList<>(items.getItems())))
                .build();
    }

}
