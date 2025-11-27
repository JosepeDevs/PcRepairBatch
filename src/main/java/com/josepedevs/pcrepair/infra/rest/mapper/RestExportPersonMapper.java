package com.josepedevs.pcrepair.infra.rest.mapper;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.rest.dto.PropertiesRequestDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RestExportPersonMapper {

    @Mapping(target = "outputFile", source = "outputFileName")
    @Mapping(target = "outputDirectory", ignore = true)
    @Mapping(target = "chunkSize", ignore = true)
    AppPropertiesReader map(PropertiesRequestDTO restPersonDto);
}
