package com.josepedevs.pcrepair.infra.database;

import com.josepedevs.pcrepair.domain.interfaces.PersonReader;
import com.josepedevs.pcrepair.domain.model.Person;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@StepScope
@RequiredArgsConstructor
public class SpringBatchPersonReaderAdapter implements ItemReader<Person> {

    private final PersonReader personReader;
    private Iterator<Person> iterator;

    @PostConstruct
    public void init() {
        // initializes iterator during the step
        this.iterator = personReader.readAll().iterator();
    }

    @Override
    public Person read() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
