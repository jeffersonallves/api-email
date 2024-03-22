package dev.jefferson.email.exceptions;

import lombok.Data;

@Data
public class ValidationError {
    private final String field;
    private final String message;
}
