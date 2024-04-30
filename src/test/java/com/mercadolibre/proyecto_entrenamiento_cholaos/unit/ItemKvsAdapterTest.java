package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemStatusException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.adapter.ItemKvsAdapter;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client.KvsClient;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database.KvsRepository;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import com.mercadolibre.restclient.exception.RestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ItemKvsAdapterTest {

    @Mock
    private KvsClient kvsClient;

    @Mock
    private KvsRepository kvsRepository;

    @Mock
    private Environment environment;

    private ItemKvsAdapter itemKvsAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        itemKvsAdapter = new ItemKvsAdapter(kvsClient, kvsRepository, "itemPath/");
    }

    @Test
    public void getById_ReturnsItem() {
        String itemId = "validItemId";
        GenericKvsDTO genericKvsDTO = GenericKvsDTO.builder()
                .id(itemId)
                .itemId(itemId)
                .userId("userId")
                .siteId("siteId")
                .build();
        when(kvsRepository.getById(itemId)).thenReturn(Mono.just(genericKvsDTO));

        Mono<Item> result = itemKvsAdapter.getById(itemId);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getId().equals(itemId))
                .verifyComplete();
    }

    @Test
    public void save_WhenItemIsActive_SavesItem() {
        Item item = Item.builder()
                .id("itemId")
                .itemId("validItemId")
                .userId("userId")
                .siteId("siteId")
                .build();
        try {
            when(kvsClient.isActive("itemPath/validItemId")).thenReturn(true);
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
        when(kvsRepository.save(any(GenericKvsDTO.class))).thenReturn(Mono.empty());

        Mono<Void> result = itemKvsAdapter.save(item);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void save_WhenItemIsNotActive_ThrowsItemStatusException() {
        Item item = Item.builder()
                .id("itemId")
                .itemId("validItemId")
                .userId("userId")
                .siteId("siteId")
                .build();
        try {
            when(kvsClient.isActive("itemPath/validItemId")).thenReturn(false);
        } catch (RestException e) {
            throw new RuntimeException(e);
        }

        Mono<Void> result = itemKvsAdapter.save(item);

        StepVerifier.create(result)
                .expectError(ItemStatusException.class)
                .verify();
    }

    @Test
    public void delete_CallsDeleteOnRepository() {
        String itemId = "validItemId";
        when(kvsRepository.delete(itemId)).thenReturn(Mono.empty());

        Mono<Void> result = itemKvsAdapter.delete(itemId);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
