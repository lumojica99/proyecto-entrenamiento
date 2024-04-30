package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends GenericException {

    public ItemNotFoundException(String description) {
        super(description, HttpStatus.NOT_FOUND);
    }
}
