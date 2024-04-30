package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.adapter;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.UserStatusException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.spi.IUserProviderPort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client.KvsClient;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database.KvsRepository;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import com.mercadolibre.restclient.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserKvsAdapter implements IUserProviderPort {

    private final KvsClient kvsClient;
    private final KvsRepository kvsRepository;
    private final String userPath;

    @Override
    public  Mono<User> getById(String id) {
        return kvsRepository.getById(id).map(genericKvsDTO -> User.builder()
                .id(genericKvsDTO.getId())
                .itemId(genericKvsDTO.getItemId())
                .userId(genericKvsDTO.getUserId())
                .siteId(genericKvsDTO.getSiteId())
                .build());
    }

    @Override
    public Mono<Void> save(User user) {
        try {
            boolean isActive = kvsClient.isActive(userPath + user.getItemId());
                        if (isActive) {
                            return kvsRepository.save(GenericKvsDTO.builder()
                                    .id(user.getId())
                                    .itemId(user.getItemId())
                                    .userId(user.getUserId())
                                    .siteId(user.getSiteId())
                                    .build()).then();
                        } else {
                            return Mono.error(new UserStatusException("El user no se puede guardar porque no está activo"));
                        }
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono<Void> delete(String id) {
        return kvsRepository.delete(id);
    }
}
