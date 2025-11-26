package com.josepedevs.pcrepair.infra.executionbeans.step;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.database.SpringBatchPersonReaderAdapter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ExportPersonStepsConfig {

    @Bean
    public Step logPropertiesStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  Tasklet logPropertiesTasklet) {
        return new StepBuilder(JobAndStepValuesEnum.LOG_PROPERTIES_STEP.getValue(), jobRepository)
                .tasklet(logPropertiesTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step exportPersonsStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            SpringBatchPersonReaderAdapter reader,
            FlatFileItemWriter<Person> writer,
            AppPropertiesReader props) {

        return new StepBuilder("exportPersonsStep", jobRepository)
                .<Person, Person>chunk(props.getChunkSize(), transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

}
