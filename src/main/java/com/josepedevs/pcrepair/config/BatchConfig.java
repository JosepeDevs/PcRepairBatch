package com.josepedevs.pcrepair.config;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AppPropertiesReader.class)
@Configuration
public class BatchConfig {

}
