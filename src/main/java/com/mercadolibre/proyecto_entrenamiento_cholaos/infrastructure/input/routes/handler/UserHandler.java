package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IUserService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics.MetricDatadogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final IUserService userService;
    private final MetricDatadogService metricDatadogService;

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        metricDatadogService.increment("action","get");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getById(serverRequest.pathVariable("id")), UserDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<UserDTO> userDTOMono = serverRequest.bodyToMono(UserDTO.class);
        metricDatadogService.increment("action","save");
        return userDTOMono.flatMap(userDTO ->
                ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.save(serverRequest.pathVariable("id"), userDTO), UserDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        metricDatadogService.increment("action","delete");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.delete(serverRequest.pathVariable("id")), UserDTO.class);
    }
}
