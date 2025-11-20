package com.josepedevs.pcrepair.tasklets;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.domain.interfaces.CsvFileWriterStrategy;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class CsvHeaderTaskletFactory {

    private final FolderCreator folderCreator;
    private final CsvFileWriterStrategy fileWriterStrategy;

    public Tasklet create(AppPropertiesReader props) {
        return (contribution, chunkContext) -> {
            final var outputFile = folderCreator.createOutputResourceIfNotExists(props).getFile();
            final var headerLine = Stream.of(PersonColumnsEnum.values())
                    .map(PersonColumnsEnum::getColumnName)
                    .collect(Collectors.joining(props.getDelimiter()));

            fileWriterStrategy.writeFile(outputFile, headerLine);
            return RepeatStatus.FINISHED;
        };
    }
}
