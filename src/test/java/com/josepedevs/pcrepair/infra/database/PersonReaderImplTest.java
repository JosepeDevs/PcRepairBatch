package com.josepedevs.pcrepair.infra.database;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.database.rowmapper.PersonRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;

import javax.sql.DataSource;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class PersonReaderImplTest {

    @Test
    void init_GivenDataSource_ThenReaderConfigured() throws Exception {
        final var dataSource = mock(DataSource.class);
        final var dbReader = new PersonReaderImpl(dataSource);

        dbReader.init();

        final Field readerField = PersonReaderImpl.class.getDeclaredField("reader");
        readerField.setAccessible(true);
        final var reader = (JdbcPagingItemReader<Person>) readerField.get(dbReader);

        assertNotNull(reader);

        final Field dataSourceField = JdbcPagingItemReader.class.getDeclaredField("dataSource");
        dataSourceField.setAccessible(true);
        final var ds = dataSourceField.get(reader);
        assertEquals(dataSource, ds);

        final Field queryProviderField = JdbcPagingItemReader.class.getDeclaredField("queryProvider");
        queryProviderField.setAccessible(true);
        final var provider = queryProviderField.get(reader);
        assertNotNull(provider);
        assertInstanceOf(OraclePagingQueryProvider.class, provider);

        final Field rowMapperField = JdbcPagingItemReader.class.getDeclaredField("rowMapper");
        rowMapperField.setAccessible(true);
        final var mapper = rowMapperField.get(reader);
        assertNotNull(mapper);
        assertEquals(PersonRowMapper.class, mapper.getClass());

        final var pageSizeField = JdbcPagingItemReader.class.getSuperclass().getDeclaredField("pageSize");
        pageSizeField.setAccessible(true);
        final var pageSize = pageSizeField.get(reader);
        assertEquals(10, pageSize);
    }

}
