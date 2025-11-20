package com.josepedevs.pcrepair.writer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWriterStrategyTest {

    @Test
    void writeFile_GivenFileAndContent_ThenContentWritten() throws IOException {
        final var tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        final var content = "header1,header2";

        final var strategy = new FileWriterStrategy();
        strategy.writeFile(tempFile, content);

        final var fileContent = java.nio.file.Files.readString(tempFile.toPath());
        assertEquals(content + "\n", fileContent);
    }

    @Test
    void writeFile_GivenInvalidFile_ThenThrowsIOException() {
        final var invalidFile = new File("/invalid_path/test.csv");
        final var content = "header1,header2";
        final var strategy = new FileWriterStrategy();

        assertThrows(IOException.class, () -> strategy.writeFile(invalidFile, content));
    }
}