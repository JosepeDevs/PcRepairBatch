package com.josepedevs.pcrepair.executionplan;

import com.josepedevs.pcrepair.decider.IncludeHeadersDecider;
import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class JobsConfigTest {

    @Test
    void exportPersonsJob_GivenMocks_ThenJobReturned() {
        final var jobRepository = mock(JobRepository.class);
        final var logPropertiesStep = mock(Step.class);
        final var exportPersonsStep = mock(Step.class);
        final var writeHeadersStep = mock(Step.class);
        final var decider = mock(IncludeHeadersDecider.class);
        final var listener = mock(JobExecutionListener.class);

        final var config = new JobsConfig();

        final Job job = config.exportPersonsJob(
                jobRepository,
                logPropertiesStep,
                exportPersonsStep,
                writeHeadersStep,
                decider,
                listener
        );

        assertNotNull(job);
        assertEquals(JobAndStepValuesEnum.JOB_NAME.getValue(), job.getName());
    }
}
