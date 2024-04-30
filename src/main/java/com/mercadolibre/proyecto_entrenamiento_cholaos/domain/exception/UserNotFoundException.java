package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException(String description) {
        super(description, HttpStatus.NOT_FOUND);
    }
}
