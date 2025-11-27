package com.josepedevs.pcrepair.application.scheduler;

import com.josepedevs.pcrepair.application.util.ExportJobParameterCreatorService;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledJobLauncher {

    private static final String COULD_NOT_EXECUTE_SCHEDULED_JOB_MORE_INFO =
            "Could not execute scheduled job, more info: {}";
    private final JobLauncher jobLauncher;
    private final Job exportPersonsJob;
    private final ExportJobParameterCreatorService exportJobParameterCreatorService;

    @Value("${batch.export.cron}")
    @Getter
    @Setter
    private String cron;

    @Scheduled(cron = "${batch.export.cron}")
    public void runScheduled() {
        runJob(null);
    }

    public void runJob(AppPropertiesReader params) {
        try {
            if (Objects.isNull(params)) {
                final var defaults = exportJobParameterCreatorService.prepareJobParameters(null);
                log.info("executing default job with cron {}", cron);
                jobLauncher.run(exportPersonsJob, defaults);
            } else {
                final var jobParams = exportJobParameterCreatorService.prepareJobParameters(params);
                log.info("executing the job with params {}", params);
                jobLauncher.run(exportPersonsJob, jobParams);
            }
        } catch (Exception e) {
            log.error(COULD_NOT_EXECUTE_SCHEDULED_JOB_MORE_INFO, e.getLocalizedMessage());
        }
    }
}
