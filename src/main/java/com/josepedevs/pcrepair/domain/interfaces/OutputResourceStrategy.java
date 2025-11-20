package com.josepedevs.pcrepair.domain.interfaces;

import com.josepedevs.pcrepair.propertyreader.AppPropertiesReader;
import org.springframework.core.io.WritableResource;

public interface OutputResourceStrategy {
    WritableResource createOutputResource(AppPropertiesReader props);

}
