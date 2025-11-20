package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.tasklets.CsvHeaderTaskletFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvHeaderStepConfig {

    private final CsvHeaderTaskletFactory taskletFactory;

    @Bean
    public Step writeHeadersStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 AppPropertiesReader props) {

        final var tasklet = taskletFactory.create(props);
        log.info("Creating Step for writing CSV headers");

        return new StepBuilder(JobAndStepValuesEnum.WRITE_HEADERS_STEP.getValue(), jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}