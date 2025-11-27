package com.josepedevs.pcrepair.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch.export")
@Data
@Builder(toBuilder = true)
public class AppPropertiesReader {

    @Builder.Default
    private String outputDirectory = "output";

    @Builder.Default
    private String outputFile = "persons.txt";

    @Builder.Default
    private String delimiter = ",";

    @Builder.Default
    private boolean includeHeaders = true;

    @Builder.Default
    private Integer chunkSize = 500;

    @Builder.Default
    private String exportFormat = "csv";
}
