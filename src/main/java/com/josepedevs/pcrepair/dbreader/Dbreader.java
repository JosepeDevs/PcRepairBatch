package com.josepedevs.pcrepair.dbreader;

import com.josepedevs.pcrepair.domain.Person;
import com.josepedevs.pcrepair.domain.PersonColumnsEnum;
import com.josepedevs.pcrepair.rowmapper.PersonRowMapper;
import com.josepedevs.pcrepair.util.FieldExtractor;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Configuration
public class Dbreader {

    private final FieldExtractor fieldExtractor;

    @Bean
    public JdbcPagingItemReader<Person> personReader(DataSource dataSource) {

        final var provider = new OraclePagingQueryProvider();
        provider.setSelectClause(
                Stream.of(PersonColumnsEnum.values())
                        .map(PersonColumnsEnum::getColumnName)
                        .collect(Collectors.joining(", "))
        );
        provider.setFromClause("persons");
        provider.setWhereClause("deleted = 0");
        provider.setSortKeys(Map.of(
                PersonColumnsEnum.ID_USER.getColumnName(), Order.ASCENDING
        ));

        final var reader = new JdbcPagingItemReader<Person>();
        reader.setDataSource(dataSource);
        reader.setPageSize(10);
        reader.setQueryProvider(provider);
        reader.setRowMapper(new PersonRowMapper());

        return reader;
    }
}
