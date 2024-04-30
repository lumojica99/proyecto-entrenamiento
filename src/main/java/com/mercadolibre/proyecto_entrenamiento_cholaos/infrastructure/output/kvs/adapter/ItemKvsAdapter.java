package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.adapter;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemStatusException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IItemProviderPort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client.KvsClient;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database.KvsRepository;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import com.mercadolibre.restclient.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ItemKvsAdapter implements IItemProviderPort {

    private final KvsClient kvsClient;
    private final KvsRepository kvsRepository;
    private final String itemPath;

    @Override
    public  Mono<Item> getById(String id) {
        return kvsRepository.getById(id).map(genericKvsDTO -> Item.builder()
                .id(genericKvsDTO.getId())
                .itemId(genericKvsDTO.getItemId())
                .userId(genericKvsDTO.getUserId())
                .siteId(genericKvsDTO.getSiteId())
                .build());
    }

    @Override
    public Mono<Void> save(Item item) {
        try {
            boolean isActive = kvsClient.isActive(itemPath + item.getItemId());
            if (isActive) {
                return kvsRepository.save(GenericKvsDTO.builder()
                        .id(item.getId())
                        .itemId(item.getItemId())
                        .userId(item.getUserId())
                        .siteId(item.getSiteId())
                        .build()).then();
            } else {
                return Mono.error(new ItemStatusException("El item no se puede guardar porque no está activo"));
            }
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono<Void> delete(String id) {
        return kvsRepository.delete(id);
    }
}
