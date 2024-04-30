package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database;

import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.ContainerKvsLowLevelClient;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.kvsclient.item.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemNotFoundException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class KvsRepository {

    private final ContainerKvsLowLevelClient kvs;

    public Mono<Void> save(GenericKvsDTO genericKvsDTO) {
        return Mono.just(genericKvsDTO)
                .map(dto -> {
                    Item item = new Item();
                    item.setKey(dto.getItemId());
                    item.setValue(dto);
                    try {
                        kvs.save(item);
                    } catch (KvsException e) {
                        throw new RuntimeException(e);
                    }
                    return item;
                })
                .then();
    }

    public  Mono<GenericKvsDTO> getById(String id) {
        try {
            return Mono.justOrEmpty(kvs.get(id))
                    .map(item -> {
                        try {
                            return item.getValueAsObjectType(GenericKvsDTO.class);
                        } catch (JsonException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .switchIfEmpty(Mono.error(new ItemNotFoundException("Objeto con id " + id + " no encontrado")));
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<Void> delete(String id) {
        try {
            kvs.delete(id);
            return Mono.empty();
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }
    }
}
