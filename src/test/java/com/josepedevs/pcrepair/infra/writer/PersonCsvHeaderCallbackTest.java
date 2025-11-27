package com.josepedevs.pcrepair.infra.writer;

import static org.assertj.core.api.Assertions.assertThat;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class PersonCsvHeaderCallbackTest {

    @Test
    void writeHeader_GivenEnum_ThenWritesCorrectHeader() throws Exception {
        final var callback = new PersonCsvHeaderCallback();
        final var writer = new StringWriter();

        callback.writeHeader(writer);

        final var result = writer.toString();

        final var expected = Stream.of(PersonColumnsEnum.values())
                .map(PersonColumnsEnum::getColumnName)
                .collect(Collectors.joining(","));

        assertThat(result).isEqualTo(expected);
    }
}
