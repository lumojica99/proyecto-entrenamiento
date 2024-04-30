package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IUserService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics.MetricDatadogService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler.UserHandler;
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

public class UserHandlerTest {

    @Mock
    private IUserService userService;

    @Mock
    private MetricDatadogService metricDatadogService;

    private UserHandler userHandler;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userHandler = new UserHandler(userService, metricDatadogService);
        RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
                .GET("/users/{id}", userHandler::getById)
                .POST("/users/{id}", userHandler::save)
                .DELETE("/users/{id}", userHandler::delete)
                .build();
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void getById_ReturnsUser() {
        String userId = "validUserId";
        UserDTO expectedUserDTO = UserDTO.builder()
                .id(userId)
                .build();
        when(userService.getById(userId)).thenReturn(Mono.just(expectedUserDTO));

        webTestClient.get().uri("/users/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDTO.class)
                .isEqualTo(expectedUserDTO);

        verify(metricDatadogService).increment("action", "get");
    }

    @Test
    public void save_CreatesUserAndReturnsCreatedStatus() {
        String userId = "validUserId";
        UserDTO requestUserDTO = UserDTO.builder()
                .id(userId)
                .build();
        when(userService.save(eq(userId), any(UserDTO.class))).thenAnswer(invocation -> {
            UserDTO userDTO = invocation.getArgument(1);
            return Mono.empty();
        });

        webTestClient.post().uri("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestUserDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDTO.class);

        verify(metricDatadogService).increment("action", "save");
    }

    @Test
    public void delete_DeletesUser() {
        String userId = "validUserId";
        when(userService.delete(userId)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(metricDatadogService).increment("action", "delete");
    }
}
