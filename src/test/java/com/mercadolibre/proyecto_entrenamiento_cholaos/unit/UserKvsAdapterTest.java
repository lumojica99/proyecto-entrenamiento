package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.adapter.UserKvsAdapter;
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

public class UserKvsAdapterTest {

    @Mock
    private KvsClient kvsClient;

    @Mock
    private KvsRepository kvsRepository;

    @Mock
    private Environment environment;

    private UserKvsAdapter userKvsAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userKvsAdapter = new UserKvsAdapter(kvsClient, kvsRepository, "userPath/");
    }

    @Test
    public void getById_ReturnsUser() {
        String userId = "validUserId";
        GenericKvsDTO genericKvsDTO = GenericKvsDTO.builder()
                .id(userId)
                .itemId(userId)
                .userId("userId")
                .siteId("siteId")
                .build();
        when(kvsRepository.getById(userId)).thenReturn(Mono.just(genericKvsDTO));

        Mono<User> result = userKvsAdapter.getById(userId);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(userId))
                .verifyComplete();
    }

    @Test
    public void save_WhenUserIsActive_SavesUser() {
        User user = User.builder()
                .id("userId")
                .itemId("validUserId")
                .userId("userId")
                .siteId("siteId")
                .build();
        try {
            when(kvsClient.isActive("userPath/validUserId")).thenReturn(true);
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
        when(kvsRepository.save(any(GenericKvsDTO.class))).thenReturn(Mono.empty());

        Mono<Void> result = userKvsAdapter.save(user);

        StepVerifier.create(result)
                .verifyComplete();
    }


    @Test
    public void delete_CallsDeleteOnRepository() {
        String userId = "validUserId";
        when(kvsRepository.delete(userId)).thenReturn(Mono.empty());

        Mono<Void> result = userKvsAdapter.delete(userId);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
