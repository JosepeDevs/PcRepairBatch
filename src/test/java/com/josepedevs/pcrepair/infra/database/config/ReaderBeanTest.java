package com.josepedevs.pcrepair.infra.database.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.josepedevs.pcrepair.domain.interfaces.PersonReader;
import com.josepedevs.pcrepair.domain.model.Person;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReaderBeanTest {

    @Test
    void personReaderItemReader_GivenEmptyList_ThenReturnsNull() throws Exception {
        final var personReaderPort = mock(PersonReader.class);
        when(personReaderPort.readAll()).thenReturn(Stream.of());
        final var readerBean = new ReaderBean();

        final var reader = readerBean.personReaderItemReader(personReaderPort);

        assertNotNull(reader);
        assertNull(reader.read());
    }

    @Test
    void personReaderItemReader_GivenListWithPersons_ThenReturnsPersonsInOrder() throws Exception {
        final var person1 = Person.builder().build();
        final var person2 = Person.builder().build();
        final var personReaderPort = mock(PersonReader.class);
        when(personReaderPort.readAll()).thenReturn(Stream.of(person1, person2));
        final var readerBean = new ReaderBean();

        final var reader = readerBean.personReaderItemReader(personReaderPort);

        final var first = reader.read();
        final var second = reader.read();
        final var third = reader.read();

        assertEquals(person1, first);
        assertEquals(person2, second);
        assertNull(third);
    }
}
