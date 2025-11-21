package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.enums.FileFormatsEnum;
import com.josepedevs.pcrepair.domain.interfaces.PersonWriterStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PersonWriterFactory {

    @Qualifier("csvPersonWriterStrategy")
    private final PersonWriterStrategy csvStrategy;

    @Qualifier("jsonPersonWriterStrategy")
    private final PersonWriterStrategy jsonStrategy;

    public PersonWriterFactory(
            @Qualifier("csvPersonWriterStrategy") PersonWriterStrategy csvStrategy,
            @Qualifier("jsonPersonWriterStrategy") PersonWriterStrategy jsonStrategy
    ) {
        this.csvStrategy = csvStrategy;
        this.jsonStrategy = jsonStrategy;
    }

    public PersonWriterStrategy getStrategy(String format) {
        final var fileFormat = FileFormatsEnum.from(format);

        return switch (fileFormat) {
            case CSV -> csvStrategy;
            case JSON -> jsonStrategy;
        };
    }
}
