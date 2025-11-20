package com.josepedevs.pcrepair.util;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class FolderCreator {

    public FileSystemResource createOutputResourceIfNotExists(AppPropertiesReader props) {
        final var dir = Paths.get(props.getOutputDirectory());
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            final var msg = String.format("Could not create output directory: %s" , dir);
            log.error(msg);
            throw new IllegalStateException(msg, e);
        }

        return new FileSystemResource(dir.resolve(props.getOutputFile()).toFile());
    }

}
