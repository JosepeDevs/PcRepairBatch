package com.josepedevs.pcrepair.infra.database;

import com.josepedevs.pcrepair.domain.enums.PersonColumnsEnum;
import com.josepedevs.pcrepair.domain.enums.PersonDatabase;
import com.josepedevs.pcrepair.domain.exceptions.BatchException;
import com.josepedevs.pcrepair.domain.interfaces.PersonReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.database.rowmapper.PersonRowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PersonReaderImpl implements PersonReader {

    private static final String ERROR_MESSAGE = "Error reading people from database. More info: %s";
    private final DataSource dataSource;

    @Override
    public Stream<Person> readAll() {
        OraclePagingQueryProvider provider = new OraclePagingQueryProvider();
        provider.setSelectClause(Stream.of(PersonColumnsEnum.values())
                .map(PersonColumnsEnum::getColumnName)
                .collect(Collectors.joining(", ")));
        provider.setFromClause(PersonDatabase.getDatabaseName());
        provider.setWhereClause(PersonColumnsEnum.DELETED.getColumnName() + "= 0");
        provider.setSortKeys(Map.of(PersonColumnsEnum.ID_USER.getColumnName(), Order.ASCENDING));

        JdbcPagingItemReader<Person> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(10);
        reader.setQueryProvider(provider);
        reader.setRowMapper(new PersonRowMapper());
        try {
            reader.afterPropertiesSet();
        } catch (Exception e) {
            throw new BatchException("could not do reader after properties were set", e.getLocalizedMessage());
        }
        reader.open(new ExecutionContext());

        List<Person> people = new ArrayList<>();
        Person p;
        try {
            while ((p = reader.read()) != null) {
                people.add(p);
            }
        } catch (Exception e) {
            final var msg = String.format(ERROR_MESSAGE, e.getLocalizedMessage());
            log.error(msg);
            throw new BatchException(msg);
        }
        return people.stream();
    }
}
