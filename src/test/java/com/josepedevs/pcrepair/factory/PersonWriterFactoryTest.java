package com.josepedevs.pcrepair.factory;

import com.josepedevs.pcrepair.domain.interfaces.PersonWriterStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PersonWriterFactoryTest {

    @Mock
    private PersonWriterStrategy csvStrategy;

    @Mock
    private PersonWriterStrategy jsonStrategy;

    private PersonWriterFactory factory;

    @BeforeEach
    void setup() {
        factory = new PersonWriterFactory(csvStrategy, jsonStrategy);
    }

    @Test
    void getStrategy_GivenCsvFormat_ThenReturnsCsvStrategy() {
        final var result = factory.getStrategy("csv");
        assertThat(result).isEqualTo(csvStrategy);
    }

    @Test
    void getStrategy_GivenJsonFormat_ThenReturnsJsonStrategy() {
        final var result = factory.getStrategy("json");
        assertThat(result).isEqualTo(jsonStrategy);
    }

    @Test
    void getStrategy_GivenUnknown_ThenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.getStrategy("xml"));
    }

    @Test
    void getStrategy_GivenNull_ThenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.getStrategy(null));
    }
}
