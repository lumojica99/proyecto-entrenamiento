package com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IItemService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;

public class ItemServiceImpl implements IItemService {

    private final IItemServicePort  iitemServicePort;

    public ItemServiceImpl(IItemServicePort iitemServicePort) {
        this.iitemServicePort = iitemServicePort;
    }
}
