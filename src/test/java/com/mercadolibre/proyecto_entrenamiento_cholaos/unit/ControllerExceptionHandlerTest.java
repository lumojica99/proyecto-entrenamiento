package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.GenericException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.advice.ControllerExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler handler;

    @Mock
    private ServerRequest request;

    @Test
    void testGetErrorAttributesWithGenericException() {
        GenericException exception = new GenericException("Mensaje de error", HttpStatus.BAD_REQUEST);
        when(request.attribute(DefaultErrorAttributes.class.getName() + ".ERROR")).thenReturn(Optional.of(exception));

        Map<String, Object> errorAttributes = handler.getErrorAttributes(request, ErrorAttributeOptions.defaults());

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorAttributes.get("status"));
        assertEquals("Mensaje de error", errorAttributes.get("mensaje"));
    }

    @Test
    void testGetErrorAttributesWithStandardException() {
        RuntimeException exception = new RuntimeException("Mensaje de error estandar");
        when(request.attribute(DefaultErrorAttributes.class.getName() + ".ERROR")).thenReturn(Optional.of(exception));

        Map<String, Object> errorAttributes = handler.getErrorAttributes(request, ErrorAttributeOptions.defaults());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorAttributes.get("status"));
        assertEquals("Mensaje de error estandar", errorAttributes.get("mensaje"));
    }
}