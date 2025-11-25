package com.josepedevs.pcrepair.infra.rest;

import com.josepedevs.pcrepair.infra.scheduler.ScheduledJobLauncher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController {

    private final ScheduledJobLauncher launcher;

    @PostMapping("/run-job")
    public ResponseEntity<String> runJob() {
        launcher.runJob();
        return ResponseEntity.ok("Job started");
    }
}