package com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.GenericException;
import org.springframework.http.HttpStatus;

public class UserServiceException extends GenericException {
    public UserServiceException(String message) {super(message, HttpStatus.BAD_REQUEST);}
}
