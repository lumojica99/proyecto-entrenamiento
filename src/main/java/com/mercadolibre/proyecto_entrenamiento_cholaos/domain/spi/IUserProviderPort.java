package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import reactor.core.publisher.Mono;

public interface IUserProviderPort {
    Mono<User> getById(String id);
    Mono<Void> save(User user);
    Mono<Void> delete(String id);
}
