package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogPropertiesTaskletTest {

    @Test
    void execute_GivenAppProperties_ThenReturnsFinishedAndLogsProperties() {
        final var props = mock(AppPropertiesReader.class);
        when(props.getOutputDirectory()).thenReturn("/tmp");
        when(props.getOutputFile()).thenReturn("file.csv");
        when(props.getDelimiter()).thenReturn(",");
        when(props.isIncludeHeaders()).thenReturn(true);

        final var tasklet = new LogPropertiesTasklet(props);
        final var contribution = mock(StepContribution.class);
        final var chunkContext = mock(ChunkContext.class);

        final var result = tasklet.execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, result);

        verify(props).getOutputDirectory();
        verify(props).getOutputFile();
        verify(props).getDelimiter();
        verify(props).isIncludeHeaders();
    }

    @Test
    void execute_GivenNullContribution_ThenThrowsNullPointerException() {
        final var props = mock(AppPropertiesReader.class);
        final var tasklet = new LogPropertiesTasklet(props);
        final var chunkContext = mock(ChunkContext.class);

        assertThrows(NullPointerException.class,
                () -> tasklet.execute(null, chunkContext));
    }

    @Test
    void execute_GivenNullChunkContext_ThenThrowsNullPointerException() {
        final var props = mock(AppPropertiesReader.class);
        final var tasklet = new LogPropertiesTasklet(props);
        final var contribution = mock(StepContribution.class);

        assertThrows(NullPointerException.class,
                () -> tasklet.execute(contribution, null));
    }
}