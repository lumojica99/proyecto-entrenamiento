package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IItemService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics.MetricDatadogService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler.ItemHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemHandlerTest {

    @Mock
    private IItemService itemService;

    @Mock
    private MetricDatadogService metricDatadogService;

    private ItemHandler itemHandler;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        itemHandler = new ItemHandler(itemService, metricDatadogService);
        RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
                .GET("/items/{id}", itemHandler::getById)
                .POST("/items/{id}", itemHandler::save)
                .DELETE("/items/{id}", itemHandler::delete)
                .build();
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void getById_ReturnsItem() {
        String itemId = "validItemId";
        ItemDTO expectedItemDTO = ItemDTO.builder()
                .id(itemId)
                .build();
        when(itemService.getById(itemId)).thenReturn(Mono.just(expectedItemDTO));

        webTestClient.get().uri("/items/{id}", itemId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ItemDTO.class)
                .isEqualTo(expectedItemDTO);

        verify(metricDatadogService).increment("action", "get");
    }

    @Test
    public void save_CreatesItemAndReturnsCreatedStatus() {
        String itemId = "validItemId";
        ItemDTO requestItemDTO = ItemDTO.builder()
                .id(itemId)
                .build();
        when(itemService.save(eq(itemId), any(ItemDTO.class))).thenAnswer(invocation -> {
            ItemDTO itemDTO = invocation.getArgument(1);
            return Mono.empty();
        });

        webTestClient.post().uri("/items/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestItemDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ItemDTO.class);

        verify(metricDatadogService).increment("action", "save");
    }

    @Test
    public void delete_DeletesItem() {
        String itemId = "validItemId";
        when(itemService.delete(itemId)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/items/{id}", itemId)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(metricDatadogService).increment("action", "delete");
    }
}
