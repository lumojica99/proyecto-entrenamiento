package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IItemService;
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
public class ItemHandler {

    private final IItemService itemService;
    private final MetricDatadogService metricDatadogService;

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        metricDatadogService.increment("action","get");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemService.getById(serverRequest.pathVariable("id")), ItemDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<ItemDTO> itemDTOMono = serverRequest.bodyToMono(ItemDTO.class);
        metricDatadogService.increment("action","save");
        return itemDTOMono.flatMap(itemDTO ->
                ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                        .body(itemService.save(serverRequest.pathVariable("id"), itemDTO), ItemDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        metricDatadogService.increment("action","delete");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemService.delete(serverRequest.pathVariable("id")), ItemDTO.class);
    }
}
