package com.josepedevs.pcrepair.writer;

import com.josepedevs.pcrepair.domain.interfaces.CsvFileWriterStrategy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileWriterStrategy implements CsvFileWriterStrategy {
    @Override
    public void writeFile(File file, String content) throws IOException {
        try (final var writer = new FileWriter(file, false)) {
            writer.write(content + "\n");
        }
    }
}
