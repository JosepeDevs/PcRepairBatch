package com.josepedevs.pcrepair.infra.writer.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josepedevs.pcrepair.application.util.FolderCreator;
import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.domain.model.Person;
import com.josepedevs.pcrepair.infra.writer.strategy.PersonWriterStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonPersonWriterStrategy implements PersonWriterStrategy {

    private final FolderCreator folderCreator;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public FlatFileItemWriter<Person> createWriter(AppPropertiesReader props) {
        final var writer = new FlatFileItemWriter<Person>();
        final var resource = folderCreator.createOutputResourceIfNotExists(props);
        writer.setResource(resource);
        writer.setAppendAllowed(false);

        writer.setHeaderCallback(w -> w.write("["));
        writer.setFooterCallback(w -> w.write("]"));

        writer.setLineAggregator(new LineAggregator<>() {

            private boolean first = true;

            @Override
            @NonNull
            public String aggregate(@NonNull Person item) {
                try {
                    String json = mapper.writeValueAsString(item);
                    if (first) {
                        first = false;
                        return json;
                    } else {
                        return "," + json;
                    }
                } catch (JsonProcessingException e) {
                    throw new IllegalStateException("Error serializing Person to JSON", e);
                }
            }
        });
        log.info("Writing JSON to {}", resource.getFile().getAbsolutePath());
        return writer;
    }
}
