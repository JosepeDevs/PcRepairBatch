package com.josepedevs.pcrepair.jobs;

import com.josepedevs.pcrepair.domain.enums.JobAndStepValuesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobsConfigTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private Step logPropertiesStep;

    @Mock
    private Step exportPersonsStep;

    @Mock
    private JobExecutionListener jobExecutionListener;

    private JobsConfig config;

    @BeforeEach
    void setup() {
        config = new JobsConfig();
    }

    @Test
    void exportPersonsJob_GivenAllDependencies_ThenJobIsBuiltCorrectly() {
        
        final var job = config.exportPersonsJob(
                jobRepository,
                logPropertiesStep,
                exportPersonsStep,
                jobExecutionListener
        );

        assertThat(job).isNotNull();
        assertThat(job.getName()).isEqualTo(JobAndStepValuesEnum.JOB_NAME.getValue());
        assertThat(job.getJobParametersIncrementer()).isNull(); // no incrementer configured
    }

    @Test
    void exportPersonsJob_GivenSteps_ThenStepsAreInCorrectOrder() {
        when(logPropertiesStep.getName()).thenReturn("logPropertiesStep");
        when(exportPersonsStep.getName()).thenReturn("exportPersonsStep");
        final Job job = config.exportPersonsJob(
                jobRepository,
                logPropertiesStep,
                exportPersonsStep,
                jobExecutionListener
        );

        assertThat(job).isInstanceOf(SimpleJob.class);
        SimpleJob simpleJob = (SimpleJob) job;

        final var steps = simpleJob.getStepNames();
        assertThat(steps).containsExactly(
                "logPropertiesStep",
                "exportPersonsStep"
        );
    }

}
