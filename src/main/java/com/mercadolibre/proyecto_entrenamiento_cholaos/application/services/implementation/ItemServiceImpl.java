package com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.ItemServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IItemService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements IItemService {

    private final IItemServicePort  iItemServicePort;


    @Override
    public Mono<ItemDTO> getById(String id) {
        log.info("Obteniendo item con ID: {}", id);
        return iItemServicePort.getById(id).map(item -> ItemDTO.builder()
                .id(item.getId())
                .itemId(item.getItemId())
                .siteId(item.getSiteId())
                .userId(item.getUserId())
                .build());
    }
    @Override
    public Mono<Void> save(String id, ItemDTO itemDTO) {
        log.info("Guardando item con ID: {}", id);
        //Se valida si el item que se quiere guardar está activo o no
        iItemServicePort.getById(id);
        String site = itemDTO.getSiteId();
        if(site.equals("MLM")||site.equals("MPE")){
            throw new ItemServiceException("No se puede guardar el site " +site);
        }
        String generatedId = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .id(generatedId)
                .itemId(id)
                .siteId(site)
                .userId(itemDTO.getUserId())
                .build();
        log.info("Creando item con UUID: {}", generatedId);
        return iItemServicePort.save(item);
    }
    @Override
    public Mono<Void> delete(String id) {
        log.info("Eliminando item con ID: {}", id);
        return iItemServicePort.delete(id);
    }

}
