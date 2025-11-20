package com.josepedevs.pcrepair.decider;

import com.josepedevs.pcrepair.domain.enums.DeciderValuesEnum;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IncludeHeadersDeciderTest {

    @Test
    void decide_GivenIncludeHeadersTrue_ThenReturnsIncludeHeadersStatus() {
        final var props = mock(AppPropertiesReader.class);
        when(props.isIncludeHeaders()).thenReturn(true);

        final var decider = new IncludeHeadersDecider(props);
        final var jobExecution = mock(JobExecution.class);
        final var stepExecution = mock(StepExecution.class);

        final FlowExecutionStatus status = decider.decide(jobExecution, stepExecution);

        assertEquals(DeciderValuesEnum.INCLUDE_HEADERS.getDeciderValue(), status.getName());
        verify(props).isIncludeHeaders();
    }

    @Test
    void decide_GivenIncludeHeadersFalse_ThenReturnsSkipHeadersStatus() {
        final var props = mock(AppPropertiesReader.class);
        when(props.isIncludeHeaders()).thenReturn(false);

        final var decider = new IncludeHeadersDecider(props);
        final var jobExecution = mock(JobExecution.class);
        final var stepExecution = mock(StepExecution.class);

        final FlowExecutionStatus status = decider.decide(jobExecution, stepExecution);

        assertEquals(DeciderValuesEnum.SKIP_HEADERS.getDeciderValue(), status.getName());
        verify(props).isIncludeHeaders();
    }
}
