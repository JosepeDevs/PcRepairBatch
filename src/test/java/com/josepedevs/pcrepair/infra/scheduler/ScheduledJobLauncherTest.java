package com.josepedevs.pcrepair.infra.scheduler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ScheduledJobLauncherTest {

    @Test
    void runJob_GivenValidDependencies_ThenJobLauncherRunsJob() throws Exception {
        final var jobLauncher = mock(JobLauncher.class);
        final var job = mock(Job.class);
        final var launcher = new ScheduledJobLauncher(jobLauncher, job);
        launcher.setCron("0 0 * * * *");

        launcher.runJob();

        verify(jobLauncher).run(eq(job), any(JobParameters.class));
    }

    @Test
    void runJob_WhenLauncherThrowsException_ThenErrorIsLoggedAndNoExceptionPropagated() throws Exception {
        final var jobLauncher = mock(JobLauncher.class);
        final var job = mock(Job.class);
        doThrow(new RuntimeException("test")).when(jobLauncher).run(any(), any());
        final var launcher = new ScheduledJobLauncher(jobLauncher, job);
        launcher.setCron("*/1 * * * * *");

        launcher.runJob();

        verify(jobLauncher).run(any(), any());
    }

    @Test
    void runScheduled_GivenSpy_ThenRunJobIsInvoked() {
        final var jobLauncher = mock(JobLauncher.class);
        final var job = mock(Job.class);
        final var launcher = Mockito.spy(new ScheduledJobLauncher(jobLauncher, job));
        launcher.setCron("* * * * *");

        launcher.runScheduled();

        verify(launcher).runJob();
    }
}
