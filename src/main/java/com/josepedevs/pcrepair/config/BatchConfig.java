package com.josepedevs.pcrepair.config;

import com.josepedevs.pcrepair.propertyreader.AppPropetiesReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AppPropetiesReader.class)
@Configuration
public class BatchConfig {

}
