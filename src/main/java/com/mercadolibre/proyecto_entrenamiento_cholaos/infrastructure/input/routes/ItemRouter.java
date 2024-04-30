package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes;

import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ItemRouter {
    private static final String path = "items";

    @Bean
    RouterFunction<ServerResponse> itemRoutes(ItemHandler itemHandler) {
        return RouterFunctions.route()
                .GET(path+"/{id}",itemHandler::getById)
                .POST(path+"/{id}",itemHandler::save)
                .DELETE(path+"/{id}", itemHandler::delete)
                .build();
    }

}
