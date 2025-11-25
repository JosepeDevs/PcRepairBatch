package com.josepedevs.pcrepair.application.step;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import com.josepedevs.pcrepair.domain.interfaces.ExportPersonPort;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExportPersonStepsConfigTest {

    @Test
    void logPropertiesStep_GivenMocks_ThenStepReturned() {
        final var jobRepository = mock(JobRepository.class);
        final var transactionManager = mock(PlatformTransactionManager.class);
        final var tasklet = mock(Tasklet.class);
        final var config = new ExportPersonStepsConfig(mock(AppPropertiesReader.class));

        final var step = config.logPropertiesStep(jobRepository, transactionManager, tasklet);

        assertNotNull(step);
        assertEquals(JobAndStepValuesEnum.LOG_PROPERTIES_STEP.getValue(), step.getName());
    }

    @Test
    void exportPersonsStep_GivenMocks_ThenStepReturned() {
        final var jobRepository = mock(JobRepository.class);
        final var transactionManager = mock(PlatformTransactionManager.class);
        final var reader = mock(ItemReader.class);
        final var exporter = mock(ExportPersonPort.class);

        final var props = mock(AppPropertiesReader.class);
        when(props.getChunkSize()).thenReturn(5);

        final var config = new ExportPersonStepsConfig(props);

        final var step = config.exportPersonsStep(jobRepository, transactionManager, reader, exporter);

        assertNotNull(step);
        assertEquals(JobAndStepValuesEnum.EXPORT_PERSON_STEP.getValue(), step.getName());
    }
}
