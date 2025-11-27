package com.josepedevs.pcrepair.infra.database;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josepedevs.pcrepair.domain.interfaces.PersonReader;
import com.josepedevs.pcrepair.domain.model.Person;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SpringBatchPersonReaderAdapterTest {

    @Mock
    private PersonReader personReader;

    private SpringBatchPersonReaderAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new SpringBatchPersonReaderAdapter(personReader);
    }

    @Test
    void read_ReturnsAllPersonsInOrderAndThenNull() {

        Person p1 = Person.builder().idUser("1").build();
        Person p2 = Person.builder().idUser("2").build();

        when(personReader.readAll()).thenReturn(Stream.of(p1, p2));

        adapter.init();

        assertSame(p1, adapter.read(), "First call should return first person");
        assertSame(p2, adapter.read(), "Second call should return second person");
        assertNull(adapter.read(), "After all items, read() should return null");

        verify(personReader).readAll();
    }

    @Test
    void read_WithEmptyStream_ReturnsNullImmediately() {

        when(personReader.readAll()).thenReturn(Stream.of());

        adapter.init();

        assertNull(adapter.read(), "Empty stream should return null on first read");
        verify(personReader).readAll();
    }
}
