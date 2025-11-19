package com.josepedevs.pcrepair.propertyreader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch.export")
@Data
public class AppPropetiesReader {
        private String outputDirectory;
        private String outputFile;
        private String delimiter = ",";
        private boolean includeHeaders = true;
        private Integer chunkSize = 500;
}
