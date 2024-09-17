package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IItemProviderPort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.useCase.ItemUseCase;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemUseCaseTest {

    @Test
    public void getById_ReturnsItemFromProviderPort() {
        String itemId = "itemIdValido";
        Item expectedItem = Item.builder()
                .id(itemId)
                .build();
        IItemProviderPort mockProviderPort = mock(IItemProviderPort.class);
        when(mockProviderPort.getById(itemId)).thenReturn(Mono.just(expectedItem));

        ItemUseCase itemUseCase = new ItemUseCase(mockProviderPort);

        Mono<Item> result = itemUseCase.getById(itemId);

        assertNotNull(result);
        assertEquals(expectedItem, result.block());
    }

    @Test
    public void save_CallsSaveOnProviderPort() {
        Item item = Item.builder()
                .id("itemId")
                .build();
        IItemProviderPort mockProviderPort = mock(IItemProviderPort.class);
        when(mockProviderPort.save(item)).thenReturn(Mono.empty());

        ItemUseCase itemUseCase = new ItemUseCase(mockProviderPort);

        Mono<Void> result = itemUseCase.save(item);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
        verify(mockProviderPort, times(1)).save(item);
    }

    @Test
    public void delete_CallsDeleteOnProviderPort() {
        String itemId = "itemIdValido";
        IItemProviderPort mockProviderPort = mock(IItemProviderPort.class);
        when(mockProviderPort.delete(itemId)).thenReturn(Mono.empty());

        ItemUseCase itemUseCase = new ItemUseCase(mockProviderPort);

        Mono<Void> result = itemUseCase.delete(itemId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
        verify(mockProviderPort, times(1)).delete(itemId);
    }
}