package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IUserProviderPort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.useCase.UserUseCase;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUseCaseTest {

    @Test
    public void getById_ReturnsUserFromProviderPort() {
        String userId = "userIdValido";
        User expectedUser = User.builder()
                .id(userId)
                .build();
        IUserProviderPort mockProviderPort = mock(IUserProviderPort.class);
        when(mockProviderPort.getById(userId)).thenReturn(Mono.just(expectedUser));

        UserUseCase userUseCase = new UserUseCase(mockProviderPort);

        Mono<User> result = userUseCase.getById(userId);

        assertNotNull(result);
        assertEquals(expectedUser, result.block());
    }

    @Test
    public void save_CallsSaveOnProviderPort() {
        User user = User.builder()
                .id("userId")
                .build();
        IUserProviderPort mockProviderPort = mock(IUserProviderPort.class);
        when(mockProviderPort.save(user)).thenReturn(Mono.empty());

        UserUseCase userUseCase = new UserUseCase(mockProviderPort);

        Mono<Void> result = userUseCase.save(user);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
        verify(mockProviderPort, times(1)).save(user);
    }

    @Test
    public void delete_CallsDeleteOnProviderPort() {
        String userId = "userIdValido";
        IUserProviderPort mockProviderPort = mock(IUserProviderPort.class);
        when(mockProviderPort.delete(userId)).thenReturn(Mono.empty());

        UserUseCase userUseCase = new UserUseCase(mockProviderPort);

        Mono<Void> result = userUseCase.delete(userId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
        verify(mockProviderPort, times(1)).delete(userId);
    }
}