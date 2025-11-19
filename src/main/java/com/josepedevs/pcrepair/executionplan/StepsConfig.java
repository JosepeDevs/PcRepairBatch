package com.josepedevs.pcrepair.executionplan;

import com.josepedevs.pcrepair.domain.Person;
import com.josepedevs.pcrepair.listener.JobCompletionLoggingListener;
import com.josepedevs.pcrepair.logtasklet.LogPropertiesTasklet;
import com.josepedevs.pcrepair.propertyreader.PropertyReader;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
public class StepsConfig {

    private final PropertyReader propertyReader;

    @Bean
    public Tasklet logPropertiesTasklet() {
        return new LogPropertiesTasklet(propertyReader);
    }

    @Bean
    public JobExecutionListener jobCompletionListener() {
        return new JobCompletionLoggingListener();
    }

    @Bean
    public Step logPropertiesStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  Tasklet logPropertiesTasklet) {
        return new StepBuilder("logPropertiesStep", jobRepository)
                .tasklet(logPropertiesTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step exportPersonsStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  JdbcPagingItemReader<Person> personReader,
                                  @Qualifier("personCsvFileWriter") FlatFileItemWriter<Person> personCsvFileWriter) {

        return new StepBuilder("exportPersonsStep", jobRepository)
                .<Person, Person>chunk(500, transactionManager)
                .reader(personReader)
                .writer(personCsvFileWriter)
                .build();
    }

}
