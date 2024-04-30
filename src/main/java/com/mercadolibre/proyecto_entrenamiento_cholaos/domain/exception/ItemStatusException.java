package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception;

import org.springframework.http.HttpStatus;

public class ItemStatusException extends GenericException {
    public ItemStatusException(String message) { super(message, HttpStatus.CONFLICT); }
}
