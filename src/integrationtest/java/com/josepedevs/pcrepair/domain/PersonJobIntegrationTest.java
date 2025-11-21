package com.josepedevs.pcrepair.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@SpringBatchTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonJobIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void clean() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void personJob_GivenValidContext_ThenJobCompletesSuccessfully() throws Exception {

        final var job = context.getBean("exportPersonsJob", Job.class);
        jobLauncherTestUtils.setJob(job);


        final var result = jobLauncherTestUtils.launchJob();


        assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    @Test
    void personJob_GivenJobHasWriter_ThenWriterBeanIsPresent() {
        final var writer = context.getBean("personFileWriter");

        assertNotNull(writer);
        assertInstanceOf(FlatFileItemWriter.class, writer);
    }
}

