package com.josepedevs.pcrepair.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Person {

    private String idUser;
    private String metadata;
    private String name;
    private String nidPassport;
    private Integer deleted;
}
