package com.josepedevs.pcrepair.strategy;

import com.josepedevs.pcrepair.domain.interfaces.OutputResourceStrategy;
import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import com.josepedevs.pcrepair.util.FolderCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FolderOutputResourceStrategy implements OutputResourceStrategy {

    private final FolderCreator folderCreator;

    @Override
    public WritableResource createOutputResource(AppPropertiesReader props) {
        return folderCreator.createOutputResourceIfNotExists(props);
    }
}