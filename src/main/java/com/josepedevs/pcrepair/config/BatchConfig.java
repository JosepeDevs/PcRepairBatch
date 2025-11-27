package com.josepedevs.pcrepair.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AppPropertiesReader.class)
@Configuration
public class BatchConfig {}
