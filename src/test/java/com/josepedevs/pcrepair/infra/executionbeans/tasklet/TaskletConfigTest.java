package com.josepedevs.pcrepair.infra.executionbeans.tasklet;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.executionbeans.listener.JobCompletionLoggingListener;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class TaskletConfigTest {

    @Test
    void logPropertiesTasklet_GivenAppPropertiesReader_ThenReturnsTasklet() {
        final var props = mock(AppPropertiesReader.class);
        final var config = new TaskletConfig(props);

        final Tasklet tasklet = config.logPropertiesTasklet();

        assertNotNull(tasklet);
        assertEquals(LogPropertiesTasklet.class, tasklet.getClass());
    }

    @Test
    void jobCompletionListener_GivenConfig_ThenReturnsListener() {
        final var props = mock(AppPropertiesReader.class);
        final var config = new TaskletConfig(props);

        final JobExecutionListener listener = config.jobCompletionListener();

        assertNotNull(listener);
        assertEquals(JobCompletionLoggingListener.class, listener.getClass());
    }
}
