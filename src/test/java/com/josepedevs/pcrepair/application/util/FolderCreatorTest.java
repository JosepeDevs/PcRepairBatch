package com.josepedevs.pcrepair.application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FolderCreatorTest {

    @TempDir
    Path tempDir;

    @Test
    void createOutputResourceIfNotExists_GivenDirectoryDoesNotExist_ThenCreatesDirectory() {
        final var props = mock(AppPropertiesReader.class);
        final var outputDir = tempDir.resolve("newDir");
        final var outputFile = "file.txt";

        when(props.getOutputDirectory()).thenReturn(outputDir.toString());
        when(props.getOutputFile()).thenReturn(outputFile);

        final var folderCreator = new FolderCreator();

        final var resource = folderCreator.createOutputResourceIfNotExists(props);

        assertTrue(Files.exists(outputDir));
        assertEquals(outputDir.resolve(outputFile).toFile(), resource.getFile());
    }

    @Test
    void createOutputResourceIfNotExists_GivenDirectoryExists_ThenReturnsResource() throws IOException {
        final var existingDir = Files.createTempDirectory(tempDir, "existing");
        final var props = mock(AppPropertiesReader.class);
        final var outputFile = "file.txt";

        when(props.getOutputDirectory()).thenReturn(existingDir.toString());
        when(props.getOutputFile()).thenReturn(outputFile);

        final var folderCreator = new FolderCreator();

        final var resource = folderCreator.createOutputResourceIfNotExists(props);

        assertEquals(existingDir.resolve(outputFile).toFile(), resource.getFile());
    }
}
