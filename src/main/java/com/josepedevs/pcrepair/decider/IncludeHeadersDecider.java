package com.josepedevs.pcrepair.decider;

import com.josepedevs.pcrepair.domain.enums.DeciderValuesEnum;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IncludeHeadersDecider implements JobExecutionDecider {

    private final AppPropertiesReader props;

    @Override
    @NonNull
    public FlowExecutionStatus decide(@NonNull JobExecution jobExecution, StepExecution stepExecution) {
        if (props.isIncludeHeaders()) {
            return new FlowExecutionStatus(DeciderValuesEnum.INCLUDE_HEADERS.getDeciderValue());
        } else {
            return new FlowExecutionStatus(DeciderValuesEnum.SKIP_HEADERS.getDeciderValue());
        }
    }
}

