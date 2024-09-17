package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.ItemServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemServiceExceptionTest {

    @Test
    public void testExceptionMessage() {
        String expectedMessage = "Mensaje de error";
        ItemServiceException exception = new ItemServiceException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
