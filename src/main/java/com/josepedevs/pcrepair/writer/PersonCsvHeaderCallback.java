package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonCsvHeaderCallback implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        final var headers = Stream.of(PersonColumnsEnum.values())
                .map(PersonColumnsEnum::getColumnName)
                .collect(Collectors.joining(","));

        writer.write(headers);
    }
}
