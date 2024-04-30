package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.GenericException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemNotFoundException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionsTest {

    @Test
    public void testGenericException() {
        String expectedMessage = "Este es un test de excepción genérica";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        GenericException exception = new GenericException(expectedMessage, expectedStatus);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    public void testItemNotFoundException() {
        String expectedMessage = "Item not found";
        ItemNotFoundException exception = new ItemNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testItemStatusException() {
        String expectedMessage = "Item status conflict";
        ItemStatusException exception = new ItemStatusException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
}
