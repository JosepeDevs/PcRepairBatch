package com.josepedevs.pcrepair.config;

import com.josepedevs.pcrepair.propertyreader.PropertyReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(PropertyReader.class)
@Configuration
public class BatchConfig {

}
