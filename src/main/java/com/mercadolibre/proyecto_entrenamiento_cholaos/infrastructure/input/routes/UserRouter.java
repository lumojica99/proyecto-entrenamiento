package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes;

import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.input.routes.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {
    private static final String path = "users";

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions.route()
                .GET(path+"/{id}",userHandler::getById)
                .POST(path+"/{id}",userHandler::save)
                .DELETE(path+"/{id}", userHandler::delete)
                .build();
    }

}
