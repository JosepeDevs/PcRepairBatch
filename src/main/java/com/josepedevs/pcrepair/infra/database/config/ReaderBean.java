package com.josepedevs.pcrepair.infra.database.config;

import com.josepedevs.pcrepair.domain.interfaces.PersonReader;
import com.josepedevs.pcrepair.domain.model.Person;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReaderBean {

    @Bean
    public ItemReader<Person> personReaderItemReader(PersonReader personReaderPort) {
        final var iterator = personReaderPort.readAll().iterator();
        return () -> iterator.hasNext() ? iterator.next() : null;
    }
}
