package com.josepedevs.pcrepair.infra.rest;

import com.josepedevs.pcrepair.application.scheduler.ScheduledJobLauncher;
import com.josepedevs.pcrepair.infra.rest.dto.PropertiesRequestDTO;
import com.josepedevs.pcrepair.infra.rest.mapper.RestExportPersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final ScheduledJobLauncher launcher;
    private final RestExportPersonMapper mapper;

    @PostMapping("/run-job")
    public ResponseEntity<String> runJob(@RequestBody PropertiesRequestDTO request) {
        try {
            launcher.runJob(mapper.map(request));
            return ResponseEntity.accepted().body("Job started");
        } catch (Exception e) {
            log.error("Error starting job: {}", e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(String.format("There was some problem with the request: %s",e.getLocalizedMessage()));
        }
    }
}
