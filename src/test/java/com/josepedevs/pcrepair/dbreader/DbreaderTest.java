package com.josepedevs.pcrepair.dbreader;

import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.rowmapper.PersonRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;

import javax.sql.DataSource;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DbreaderTest {

    @Test
    void personReader_GivenDataSource_ThenReaderConfigured() throws Exception {
        final var dataSource = mock(DataSource.class);
        final var dbreader = new Dbreader();

        final JdbcPagingItemReader<Person> reader = dbreader.personReader(dataSource);

        assertNotNull(reader);

        final Field dataSourceField = JdbcPagingItemReader.class.getDeclaredField("dataSource");
        dataSourceField.setAccessible(true);
        final Object ds = dataSourceField.get(reader);
        assertEquals(dataSource, ds);

        final Field queryProviderField = JdbcPagingItemReader.class.getDeclaredField("queryProvider");
        queryProviderField.setAccessible(true);
        final Object provider = queryProviderField.get(reader);
        assertNotNull(provider);
        assertInstanceOf(OraclePagingQueryProvider.class, provider);

        final Field rowMapperField = JdbcPagingItemReader.class.getDeclaredField("rowMapper");
        rowMapperField.setAccessible(true);
        final Object mapper = rowMapperField.get(reader);
        assertNotNull(mapper);
        assertEquals(PersonRowMapper.class, mapper.getClass());
    }
}

