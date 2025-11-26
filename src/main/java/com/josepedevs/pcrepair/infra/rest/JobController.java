package com.josepedevs.pcrepair.infra.rest;

import com.josepedevs.pcrepair.application.scheduler.ScheduledJobLauncher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final ScheduledJobLauncher launcher;

    @PostMapping("/run-job")
    public ResponseEntity<String> runJob() {
        log.info("Received call to export.");
        launcher.runJob();
        return ResponseEntity.accepted().body("Job started");
    }
}