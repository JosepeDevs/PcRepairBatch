package com.josepedevs.pcrepair.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobAndStepValuesEnumTest {

    @Test
    void jobAndStepValuesEnum_GivenEnumConstants_ThenValuesCorrect() {
        final var jobName = JobAndStepValuesEnum.JOB_NAME;
        final var writeHeadersStep = JobAndStepValuesEnum.WRITE_HEADERS_STEP;
        final var logPropertiesStep = JobAndStepValuesEnum.LOG_PROPERTIES_STEP;
        final var exportPersonStep = JobAndStepValuesEnum.EXPORT_PERSON_STEP;

        assertEquals("exportPersonsJob", jobName.getValue());
        assertEquals("writeHeadersStep", writeHeadersStep.getValue());
        assertEquals("logPropertiesStep", logPropertiesStep.getValue());
        assertEquals("exportPersonsStep", exportPersonStep.getValue());
    }

    @Test
    void jobAndStepValuesEnum_ValuesArray_ThenContainsAllEnums() {
        final var values = JobAndStepValuesEnum.values();

        assertArrayEquals(new JobAndStepValuesEnum[]{
                JobAndStepValuesEnum.JOB_NAME,
                JobAndStepValuesEnum.WRITE_HEADERS_STEP,
                JobAndStepValuesEnum.LOG_PROPERTIES_STEP,
                JobAndStepValuesEnum.EXPORT_PERSON_STEP
        }, values);
    }
}
