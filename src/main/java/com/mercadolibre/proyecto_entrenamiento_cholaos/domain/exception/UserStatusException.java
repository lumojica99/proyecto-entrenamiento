package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception;

import org.springframework.http.HttpStatus;

public class UserStatusException extends GenericException {
    public UserStatusException(String message) { super(message, HttpStatus.CONFLICT); }
}
