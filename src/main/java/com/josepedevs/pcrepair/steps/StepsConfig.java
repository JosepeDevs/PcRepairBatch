package com.josepedevs.pcrepair.steps;

import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@AllArgsConstructor
@Configuration
public class StepsConfig {

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
                                  JdbcPagingItemReader<Person> personReader,
                                  FlatFileItemWriter<Person> personCsvFileWriter) {

        return new StepBuilder(JobAndStepValuesEnum.EXPORT_PERSON_STEP.getValue(), jobRepository)
                .<Person, Person>chunk(appPropertiesReader.getChunkSize(), transactionManager)
                .reader(personReader)
                .writer(personCsvFileWriter)
                .build();
    }

}
