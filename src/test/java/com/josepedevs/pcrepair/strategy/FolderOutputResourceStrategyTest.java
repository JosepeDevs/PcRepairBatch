package com.josepedevs.pcrepair.strategy;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FolderOutputResourceStrategyTest {

    @Mock
    private FolderCreator folderCreator;

    @Test
    void createOutputResource_GivenProps_ThenResourceReturned() {
        final var props = mock(AppPropertiesReader.class);
        final var resource = mock(FileSystemResource.class);
        when(folderCreator.createOutputResourceIfNotExists(props)).thenReturn(resource);

        final var strategy = new FolderOutputResourceStrategy(folderCreator);

        final var result = strategy.createOutputResource(props);

        assertSame(resource, result);
    }
}