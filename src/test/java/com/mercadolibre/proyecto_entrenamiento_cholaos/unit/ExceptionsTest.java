package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.ItemServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.UserServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.*;
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
        String expectedMessage = "Item no encontrado";
        ItemNotFoundException exception = new ItemNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testUserNotFoundException() {
        String expectedMessage = "Item no encontrado";
        UserNotFoundException exception = new UserNotFoundException(expectedMessage);

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

    @Test
    public void testUserStatusException() {
        String expectedMessage = "User status conflict";
        UserStatusException exception = new UserStatusException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
    @Test
    public void testUserExceptionMessage() {
        String expectedMessage = "Mensaje de error";
        UserServiceException exception = new UserServiceException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testItemExceptionMessage() {
        String expectedMessage = "Mensaje de error";
        ItemServiceException exception = new ItemServiceException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
