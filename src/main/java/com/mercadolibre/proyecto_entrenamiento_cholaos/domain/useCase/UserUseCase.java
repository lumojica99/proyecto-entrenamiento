package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.useCase;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IUserServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IUserProviderPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UserUseCase implements IUserServicePort {

    private final IUserProviderPort userProviderPort;

    public UserUseCase(IUserProviderPort userProviderPort) {
        this.userProviderPort = userProviderPort;
    }


    @Override
    public Mono<User> getById(String id) {
        return userProviderPort.getById(id);
    }

    @Override
    public Mono<Void> save(User user) {
        return userProviderPort.save(user);
    }

    @Override
    public Mono<Void> delete(String id) {
        return userProviderPort.delete(id);
    }
}
