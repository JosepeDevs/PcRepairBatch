package com.josepedevs.pcrepair.application.scheduler;

import com.josepedevs.pcrepair.application.util.ExportJobParameterCreatorService;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduledJobLauncherTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job exportPersonsJob;

    @Mock
    private ExportJobParameterCreatorService exportJobParameterCreatorService;

    @InjectMocks
    private ScheduledJobLauncher launcher;

    @Test
    void runJob_GivenNullParams_ThenJobLauncherRunsJobWithDefaults() {
        final var defaultParams = mock(JobParameters.class);
        when(exportJobParameterCreatorService.prepareJobParameters(null)).thenReturn(defaultParams);
        launcher.setCron("0 0 * * * *");

        launcher.runJob(null);

        assertAll(
                () -> verify(exportJobParameterCreatorService).prepareJobParameters(null),
                () -> verify(jobLauncher).run(exportPersonsJob, defaultParams)
        );
    }

    @Test
    void runJob_GivenParams_ThenJobLauncherRunsJobWithProvidedParams() {
        final var props = mock(AppPropertiesReader.class);
        final var jobParams = mock(JobParameters.class);
        when(exportJobParameterCreatorService.prepareJobParameters(props)).thenReturn(jobParams);
        launcher.setCron("0 0 * * * *");

        launcher.runJob(props);

        assertAll(
                () -> verify(exportJobParameterCreatorService).prepareJobParameters(props),
                () -> verify(jobLauncher).run(exportPersonsJob, jobParams)
        );
    }

    @Test
    void runJob_WhenLauncherThrowsException_ThenErrorLoggedAndNoExceptionPropagated() throws Exception {
        final var defaultParams = mock(JobParameters.class);
        when(exportJobParameterCreatorService.prepareJobParameters(null)).thenReturn(defaultParams);
        doThrow(new RuntimeException("boom")).when(jobLauncher).run(exportPersonsJob, defaultParams);

        launcher.runJob(null);

        verify(jobLauncher).run(exportPersonsJob, defaultParams);
    }

    @Test
    void runScheduled_GivenSpy_ThenRunJobInvokedWithNull() {
        final var spyLauncher = spy(launcher);
        spyLauncher.setCron("* * * * *");

        doNothing().when(spyLauncher).runJob(null);

        spyLauncher.runScheduled();

        verify(spyLauncher).runJob(null);
    }
}
