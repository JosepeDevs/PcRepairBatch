package com.josepedevs.pcrepair.listener;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JobCompletionLoggingListenerTest {

    @Test
    void afterJob_GivenCompletedJob_ThenLogsStepsAndJobFinished() {
        final var stepExecution = mock(StepExecution.class);
        when(stepExecution.getStepName()).thenReturn("step1");
        when(stepExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(stepExecution.getFailureExceptions()).thenReturn(List.of());

        final var jobExecution = mock(JobExecution.class);
        when(jobExecution.getStepExecutions()).thenReturn(List.of(stepExecution));
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        final var listener = new JobCompletionLoggingListener();
        listener.afterJob(jobExecution);

        verify(jobExecution).getStepExecutions();
        verify(stepExecution).getStepName();
        verify(stepExecution).getStatus();
        verify(stepExecution).getFailureExceptions();
        verify(jobExecution).getStatus();
    }

    @Test
    void afterJob_GivenFailedJobWithException_ThenLogsStepsAndJobFailed() {
        final var exception = new RuntimeException("fail");
        final var stepExecution = mock(StepExecution.class);
        when(stepExecution.getStepName()).thenReturn("step1");
        when(stepExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        when(stepExecution.getFailureExceptions()).thenReturn(List.of(exception));

        final var jobExecution = mock(JobExecution.class);
        when(jobExecution.getStepExecutions()).thenReturn(List.of(stepExecution));
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);

        final var listener = new JobCompletionLoggingListener();
        listener.afterJob(jobExecution);

        verify(jobExecution).getStepExecutions();
        verify(stepExecution).getStepName();
        verify(stepExecution).getStatus();
        verify(stepExecution).getFailureExceptions();
        verify(jobExecution).getStatus();
    }
}
