package com.josepedevs.pcrepair.infra.executionbeans.step;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.database.SpringBatchPersonReaderAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExportPersonStepsConfigTest {

    private ExportPersonStepsConfig config;

    @BeforeEach
    void setUp() {
        config = new ExportPersonStepsConfig();
    }

    @Test
    void logPropertiesStep_GivenDependencies_ThenCreatesStep() {
        final var jobRepository = mock(JobRepository.class);
        final var transactionManager = mock(PlatformTransactionManager.class);
        final var tasklet = mock(Tasklet.class);

        final var step = config.logPropertiesStep(jobRepository, transactionManager, tasklet);

        assertAll(
                () -> assertNotNull(step),
                () -> assertEquals("logPropertiesStep", step.getName())
        );
    }

    @Test
    void exportPersonsStep_GivenDependencies_ThenCreatesStepWithReaderWriter() {
        final var jobRepository = mock(JobRepository.class);
        final var transactionManager = mock(PlatformTransactionManager.class);
        final var reader = mock(SpringBatchPersonReaderAdapter.class);
        final var writer = mock(FlatFileItemWriter.class);
        final var props = mock(AppPropertiesReader.class);

        when(props.getChunkSize()).thenReturn(10);

        final var step = config.exportPersonsStep(jobRepository, transactionManager, reader, writer, props);

        assertAll(
                () -> assertNotNull(step),
                () -> assertEquals("exportPersonsStep", step.getName())
        );
    }
}
