package com.josepedevs.pcrepair.infra.rest;

import com.josepedevs.pcrepair.application.scheduler.ScheduledJobLauncher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JobControllerTest {

    @Test
    void runJob_GivenRequest_ThenLauncherInvokedAndResponseReturned() {
        final var launcher = mock(ScheduledJobLauncher.class);
        final var controller = new JobController(launcher);

        final var response = controller.runJob();

        verify(launcher).runJob();
        assertEquals("Job started", response.getBody());
        assertEquals(HttpStatusCode.valueOf(202), response.getStatusCode());
    }
}
