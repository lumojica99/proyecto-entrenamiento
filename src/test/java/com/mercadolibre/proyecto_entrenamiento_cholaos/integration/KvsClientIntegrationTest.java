package com.mercadolibre.proyecto_entrenamiento_cholaos.integration;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemNotFoundException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client.KvsClient;
import com.mercadolibre.restclient.MeliRestClient;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.exception.RestException;
import com.mercadolibre.restclient.http.Headers;
import com.mercadolibre.restclient.util.MeliContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KvsClientIntegrationTest {

    @Autowired
    private KvsClient kvsClient;

    @MockBean
    private MeliRestClient meliRestClient;

    @BeforeEach
    public void setup() {
        try {
            when(meliRestClient.get(anyString(), any(MeliContext.class)))
                    .thenReturn(new Response(200, new Headers(),"active".getBytes(StandardCharsets.UTF_8)));
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsActiveTrue() throws Exception {
        when(meliRestClient.get(contains("items/testItem"), any(MeliContext.class)))
                .thenReturn(new Response(200, new Headers(), "status=active".getBytes(StandardCharsets.UTF_8)));

        assertTrue(kvsClient.isActive("items/testItem"));
    }

    @Test
    public void testIsActiveWithMultipleAttributes() {
        try {
            when(meliRestClient.get(contains("items/multiAttributeItem"), any(MeliContext.class)))
                    .thenReturn(new Response(200, new Headers(), "status=active;other=info".getBytes(StandardCharsets.UTF_8)));
        } catch (RestException e) {
            throw new RuntimeException(e);
        }

        try {
            assertTrue(kvsClient.isActive("items/multiAttributeItem"));
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testThrowsItemNotFoundExceptionWhen404InStatus() {
        try {
            when(meliRestClient.get(contains("items/notFoundItem"), any(MeliContext.class)))
                    .thenReturn(new Response(200, new Headers(), "error=404;status=Not Found".getBytes(StandardCharsets.UTF_8)));
        } catch (RestException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ItemNotFoundException.class, () -> kvsClient.isActive("items/notFoundItem"));
    }

    @Test
    public void testHandlesRestExceptionProperly() {
        try {
            when(meliRestClient.get(contains("items/serverError"), any(MeliContext.class)))
                    .thenThrow(new RestException("Internal Server Error"));
        } catch (RestException e) {
            throw new RuntimeException(e);
        }

        assertThrows(RestException.class, () -> kvsClient.isActive("items/serverError"));
    }
}