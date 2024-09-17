package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.kvsclient.ContainerKvsLowLevelClient;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.kvsclient.item.Item;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemNotFoundException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database.KvsRepository;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KvsRepositoryTest {

    @Mock
    private ContainerKvsLowLevelClient kvs;

    @InjectMocks
    private KvsRepository repository;

    private GenericKvsDTO sampleDto;

    @BeforeEach
    void setUp() {
        sampleDto = new GenericKvsDTO("123", "itemId", "userId","siteId");
    }

    @Test
    void saveSuccess()  {
        try {
            doNothing().when(kvs).save(any(Item.class));
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }

        Mono<Void> result = repository.save(sampleDto);

        StepVerifier.create(result)
                .verifyComplete();

        try {
            verify(kvs, times(1)).save(any(Item.class));
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveThrowsKvsException() {
        try {
            doThrow(new KvsException("Storage failure")).when(kvs).save(any(Item.class));
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }

        Mono<Void> result = repository.save(sampleDto);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        try {
            verify(kvs, times(1)).save(any(Item.class));
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getByIdFound() throws Exception {
        GenericKvsDTO dto = GenericKvsDTO.builder()
                .itemId("123")
                .userId("user1")
                .siteId("siteA")
                .build();
        Item foundItem = new Item("123", dto);
        when(kvs.get("123")).thenReturn(foundItem);

        Mono<GenericKvsDTO> result = repository.getById("123");

        StepVerifier.create(result)
                .assertNext(foundDto -> {
                    assertEquals("123", foundDto.getItemId());
                    assertEquals("user1", foundDto.getUserId());
                    assertEquals("siteA", foundDto.getSiteId());
                })
                .verifyComplete();
    }

    @Test
    void getByIdNotFound() {
        try {
            when(kvs.get("unknown")).thenReturn(null);
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }

        Mono<GenericKvsDTO> result = repository.getById("unknown");

        StepVerifier.create(result)
                .expectError(ItemNotFoundException.class)
                .verify();
    }

    @Test
    void deleteSuccessfully() {
        try {
            doNothing().when(kvs).delete("123");
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }

        Mono<Void> result = repository.delete("123");

        StepVerifier.create(result)
                .verifyComplete();

        try {
            verify(kvs, times(1)).delete("123");
        } catch (KvsException e) {
            throw new RuntimeException(e);
        }
    }



}
