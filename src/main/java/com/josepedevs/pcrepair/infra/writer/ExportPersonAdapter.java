package com.josepedevs.pcrepair.infra.writer;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.interfaces.ExportPersonPort;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExportPersonAdapter implements ExportPersonPort {

    private final PersonWriterFactory writerFactory;
    private final AppPropertiesReader props;

    private FlatFileItemWriter<Person> writer;

    @PostConstruct
    void init() {
        var strategy = writerFactory.getStrategy(props.getExportFormat());
        this.writer = strategy.createWriter(props);
        this.writer.open(new ExecutionContext());
    }

    @Override
    public void export(List<Person> people) {
        try{
            writer.open(new ExecutionContext());
            for (Person p : people) {
                writer.write(new Chunk<>(p));
            }
        } catch (Exception e) {
            log.error("Could not chunk of people {}", people);
        } finally {
            writer.close();
        }
    }
}
