package com.josepedevs.pcrepair.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JobAndStepValuesEnum {

    JOB_NAME("exportPersonsJob"),
    WRITE_HEADERS_STEP("writeHeadersStep"),
    LOG_PROPERTIES_STEP("logPropertiesStep"),
    EXPORT_PERSON_STEP("exportPersonsStep");

    private final String value;
}
