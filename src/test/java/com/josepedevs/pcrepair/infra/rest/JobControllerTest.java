package com.josepedevs.pcrepair.infra.rest;

import com.josepedevs.pcrepair.application.scheduler.ScheduledJobLauncher;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.rest.dto.PropertiesRequestDTO;
import com.josepedevs.pcrepair.infra.rest.mapper.RestExportPersonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private ScheduledJobLauncher launcher;

    @Mock
    private RestExportPersonMapper mapper;

    @InjectMocks
    private JobController controller;

    @Test
    void runJob_GivenValidRequest_ThenLauncherInvokedAndAcceptedReturned() {
        final var request = mock(PropertiesRequestDTO.class);
        final var mappedProps = AppPropertiesReader.builder().build();

        when(mapper.map(request)).thenReturn(mappedProps);

        final var response = controller.runJob(request);

        assertAll(
                () -> verify(mapper).map(request),
                () -> verify(launcher).runJob(mappedProps),
                () -> assertEquals("Job started", response.getBody()),
                () -> assertEquals(HttpStatusCode.valueOf(202), response.getStatusCode())
        );
    }

    @Test
    void runJob_GivenLauncherThrowsException_ThenInternalServerErrorReturned() {
        final var request = mock(PropertiesRequestDTO.class);
        final var mappedProps = AppPropertiesReader.builder().build();

        when(mapper.map(request)).thenReturn(mappedProps);
        doThrow(new RuntimeException("boom")).when(launcher).runJob(mappedProps);

        final var response = controller.runJob(request);

        assertAll(
                () -> assertEquals("There was some problem with the request.", response.getBody()),
                () -> assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode())
        );
    }
}
