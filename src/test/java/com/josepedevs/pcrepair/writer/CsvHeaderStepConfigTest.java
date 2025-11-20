package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.tasklets.CsvHeaderTaskletFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvHeaderStepConfigTest {

    @Mock
    private CsvHeaderTaskletFactory taskletFactory;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private AppPropertiesReader props;

    @Mock
    private Tasklet tasklet;

    @Test
    void writeHeadersStep_GivenProps_ThenStepReturned() {
        when(taskletFactory.create(props)).thenReturn(tasklet);

        final var config = new CsvHeaderStepConfig(taskletFactory);

        final var step = config.writeHeadersStep(jobRepository, transactionManager, props);

        assertNotNull(step);
    }
}