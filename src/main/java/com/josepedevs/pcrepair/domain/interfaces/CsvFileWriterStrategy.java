package com.josepedevs.pcrepair.domain.interfaces;

import java.io.File;
import java.io.IOException;

public interface CsvFileWriterStrategy {
    void writeFile(File file, String content) throws IOException;
}
