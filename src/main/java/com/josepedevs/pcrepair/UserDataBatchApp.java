package com.josepedevs.pcrepair;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class UserDataBatchApp {
    public static void main(String[] args) {
        SpringApplication.run(UserDataBatchApp.class, args);
    }
}
