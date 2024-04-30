package com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import reactor.core.publisher.Mono;

public interface IItemService {

    Mono<ItemDTO> getById(String id);
    Mono<Void> save(String id, ItemDTO itemDTO);
    Mono<Void> delete(String id);

}
