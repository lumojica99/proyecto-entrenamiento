package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import reactor.core.publisher.Mono;

public interface IItemProviderPort {
    Mono<Item> getById(String id);
    Mono<Void> save(Item item);
    Mono<Void> delete(String id);
}
