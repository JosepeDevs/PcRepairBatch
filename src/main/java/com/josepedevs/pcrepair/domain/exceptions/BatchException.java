package com.josepedevs.pcrepair.domain.exceptions;

import lombok.Getter;

@Getter
public class BatchException extends RuntimeException {

    private final String executionId;

    public BatchException(String executionId, String message) {
        super(message);
        this.executionId = executionId;
    }

    public BatchException(String message) {
        this(null, message);
    }
}
