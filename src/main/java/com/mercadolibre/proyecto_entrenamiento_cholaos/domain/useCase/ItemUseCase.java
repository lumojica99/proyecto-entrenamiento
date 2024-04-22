package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.useCase;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IItemPersistencePort;

public class ItemUseCase implements IItemServicePort {

    private final IItemPersistencePort itemPersistencePort;

    public ItemUseCase(IItemPersistencePort itemPersistencePort) {
        this.itemPersistencePort = itemPersistencePort;
    }


}
