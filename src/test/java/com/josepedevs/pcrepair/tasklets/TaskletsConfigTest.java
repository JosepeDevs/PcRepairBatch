package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.listener.JobCompletionLoggingListener;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class TaskletsConfigTest {

    @Test
    void logPropertiesTasklet_GivenAppPropertiesReader_ThenReturnsTasklet() {
        final var props = mock(AppPropertiesReader.class);
        final var config = new TaskletsConfig(props);

        final Tasklet tasklet = config.logPropertiesTasklet();

        assertNotNull(tasklet);
        assertEquals(LogPropertiesTasklet.class, tasklet.getClass());
    }

    @Test
    void jobCompletionListener_GivenConfig_ThenReturnsListener() {
        final var props = mock(AppPropertiesReader.class);
        final var config = new TaskletsConfig(props);

        final JobExecutionListener listener = config.jobCompletionListener();

        assertNotNull(listener);
        assertEquals(JobCompletionLoggingListener.class, listener.getClass());
    }
}
