package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.ItemServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation.ItemServiceImpl;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private IItemServicePort iItemServicePort;

    @Mock
    private ItemServiceImpl itemService;

    @Test
    public void getById_WhenIdExists_ReturnsItemDTO() {
        String itemId = "IdValido";
        Item item = Item.builder()
                .id("itemId")
                .itemId(itemId)
                .siteId("siteId")
                .userId("userId")
                .build();
        IItemServicePort mockPort = mock(IItemServicePort.class);
        when(mockPort.getById(itemId)).thenReturn(Mono.just(item));

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        Mono<ItemDTO> result = itemService.getById(itemId);

        assertNotNull(result);
        ItemDTO itemDTO = result.block();
        assertNotNull(itemDTO);
        assertEquals(itemId, itemDTO.getItemId());
    }

    @Test
    public void getById_WhenIdDoesNotExist_ReturnsEmptyMono() {
        String itemId = "idInexistente";
        IItemServicePort mockPort = mock(IItemServicePort.class);
        when(mockPort.getById(itemId)).thenReturn(Mono.empty());

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        Mono<ItemDTO> result = itemService.getById(itemId);

        assertNotNull(result);
        assertNull(result.block());
    }

    @Test
    public void save_WhenValidSiteProvided_SavesItemAndReturnsVoid() {
        String itemId = "IteIdValido";
        String siteId = "SiteIdValido";
        String userId = "UserIdValido";
        ItemDTO itemDTO = ItemDTO.builder()
                .siteId(siteId)
                .userId(userId)
                .build();
        Item item = Item.builder()
                .id("IdGenerado")
                .itemId(itemId)
                .siteId(siteId)
                .userId(userId)
                .build();
        IItemServicePort mockPort = mock(IItemServicePort.class);
        when(mockPort.getById(itemId)).thenReturn(Mono.empty());
        when(mockPort.save(any(Item.class))).thenReturn(Mono.empty());

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        Mono<Void> result = itemService.save(itemId, itemDTO);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    public void save_WhenInvalidSiteProvided_ThrowsItemServiceException() {

        String itemId = "ItemIdValido";
        String invalidSiteId = "MLM";
        String userId = "UserIdValido";
        ItemDTO itemDTO = ItemDTO.builder()
                .siteId(invalidSiteId)
                .userId(userId)
                .build();
        IItemServicePort mockPort = mock(IItemServicePort.class);

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        assertThrows(ItemServiceException.class, () -> itemService.save(itemId, itemDTO));
    }

    @Test
    public void delete_WhenExistingItemIdProvided_DeletesItemAndReturnsVoid() {
        String itemId = "ItemIdValido";
        IItemServicePort mockPort = mock(IItemServicePort.class);
        when(mockPort.delete(itemId)).thenReturn(Mono.empty());

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        Mono<Void> result = itemService.delete(itemId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    public void delete_WhenNonexistentItemIdProvided_ReturnsVoid() {
        String nonexistentItemId = "ItemIdInexistente";
        IItemServicePort mockPort = mock(IItemServicePort.class);
        when(mockPort.delete(nonexistentItemId)).thenReturn(Mono.empty());

        ItemServiceImpl itemService = new ItemServiceImpl(mockPort);

        Mono<Void> result = itemService.delete(nonexistentItemId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }


}