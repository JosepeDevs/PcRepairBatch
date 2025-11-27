package com.josepedevs.pcrepair.infra.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.josepedevs.pcrepair.domain.exceptions.BatchException;
import com.josepedevs.pcrepair.domain.model.Person;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonReaderImplTest {

    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
    }

    @Test
    void readAll_GivenEmptyDatabase_ThenReturnsEmptyStream() {
        final var emptyReader = new PersonReaderImpl(dataSource) {
            @Override
            public Stream<Person> readAll() {
                return Stream.empty();
            }
        };

        final var result = emptyReader.readAll();

        assertAll(() -> assertNotNull(result), () -> assertEquals(0, result.count()));
    }

    @Test
    void readAll_GivenDatabaseWithPersons_ThenReturnsPersonStream() {
        final var person1 = Person.builder().build();
        final var person2 = Person.builder().build();
        final var readerWithData = new PersonReaderImpl(dataSource) {
            @Override
            public Stream<Person> readAll() {
                return Stream.of(person1, person2);
            }
        };

        final var result = readerWithData.readAll();

        assertAll(() -> assertNotNull(result), () -> assertEquals(2, result.count()));
    }

    @Test
    void readAll_GivenReaderThrowsException_ThenThrowsBatchException() {
        final var msg = "simulated";
        final var readerWithException = new PersonReaderImpl(dataSource) {
            @Override
            public Stream<Person> readAll() {
                throw new BatchException("Failed", msg);
            }
        };

        final var exception = assertThrows(BatchException.class, readerWithException::readAll);

        assertAll(() -> assertNotNull(exception), () -> assertEquals(msg, exception.getMessage()));
    }
}
