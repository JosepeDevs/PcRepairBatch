package com.josepedevs.pcrepair.joblauncher;

import com.josepedevs.pcrepair.jobs.joblauncher.JobLauncherConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JobLauncherConfigTest {

    @Test
    void runJob_GivenMocks_ThenJobLauncherRunCalled() throws Exception {
        final var jobLauncher = mock(JobLauncher.class);
        final var job = mock(Job.class);

        final var config = new JobLauncherConfig();
        final var runner = config.runJob(jobLauncher, job);

        assertNotNull(runner);

        runner.run();

        verify(jobLauncher).run(eq(job), any(JobParameters.class));
    }

    @Test
    void runJob_GivenJobLauncherThrowsException_ThenExceptionHandled() throws Exception {
        final var jobLauncher = mock(JobLauncher.class);
        final var job = mock(Job.class);

        doThrow(new RuntimeException("fail")).when(jobLauncher).run(eq(job), any(JobParameters.class));

        final var config = new JobLauncherConfig();
        final var runner = config.runJob(jobLauncher, job);

        assertDoesNotThrow(() -> runner.run());

        verify(jobLauncher).run(eq(job), any(JobParameters.class));
    }
}
