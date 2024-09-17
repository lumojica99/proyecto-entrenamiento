package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.useCase;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IItemProviderPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class ItemUseCase implements IItemServicePort {

    private final IItemProviderPort itemProviderPort;

    public ItemUseCase(IItemProviderPort itemProviderPort) {
        this.itemProviderPort = itemProviderPort;
    }

    @Override
    public Mono<Item> getById(String id) {
        return itemProviderPort.getById(id);
    }

    @Override
    public Mono<Void> save(Item item) {
        return itemProviderPort.save(item);
    }

    @Override
    public Mono<Void> delete(String id) {
        return itemProviderPort.delete(id);
    }
}
