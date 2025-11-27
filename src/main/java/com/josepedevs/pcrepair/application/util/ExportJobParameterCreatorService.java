package com.josepedevs.pcrepair.application.util;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class ExportJobParameterCreatorService {

    public JobParameters prepareJobParameters(AppPropertiesReader props) {
        if(Objects.isNull(props)) {
            props = AppPropertiesReader.builder().build();
        }
        return new JobParametersBuilder()
            .addString("outputFileName", props.getOutputFile())
            .addString("delimiter", props.getDelimiter())
            .addString("includeHeaders", String.valueOf(props.isIncludeHeaders()))
            .addString("exportFormat", props.getExportFormat())
            .addString("run.id", UUID.randomUUID() + "_" + System.currentTimeMillis(), true)
            .toJobParameters();
    }
}
