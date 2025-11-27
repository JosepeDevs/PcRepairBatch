package com.josepedevs.pcrepair.infra.rest.dto;

public record PropertiesRequestDTO(
        String outputFileName, String delimiter, String includeHeaders, String exportFormat) {
    public PropertiesRequestDTO() {
        this("persons.txt", ",", "true", "csv");
    }
}
