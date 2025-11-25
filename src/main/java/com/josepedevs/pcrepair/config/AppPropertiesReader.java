package com.josepedevs.pcrepair.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch.export")
@Data
public class AppPropertiesReader {
        private String outputDirectory = "output";
        private String outputFile = "persons.txt";
        private String delimiter = ",";
        private boolean includeHeaders = true;
        private Integer chunkSize = 500;
        private String exportFormat = "csv";
}
