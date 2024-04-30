package com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.UserServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces.IUserService;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IUserServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserServicePort iUserServicePort;


    @Override
    public Mono<UserDTO> getById(String id) {
        log.info("Obteniendo user con ID: {}", id);
        return iUserServicePort.getById(id).map(user -> UserDTO.builder()
                .id(user.getId())
                .itemId(user.getItemId())
                .siteId(user.getSiteId())
                .userId(user.getUserId())
                .build());
    }
    @Override
    public Mono<Void> save(String id, UserDTO userDTO) {
        log.info("Guardando user con ID: {}", id);
        //Se valida si el user que se quiere guardar está activo o no
        iUserServicePort.getById(id);
        String site = userDTO.getSiteId();
        if(site.equals("MLM")||site.equals("MPE")){
            throw new UserServiceException("No se puede guardar el site " +site);
        }
        String generatedId = String.valueOf(UUID.randomUUID());
        User user = User.builder()
                .id(generatedId)
                .itemId(id)
                .siteId(site)
                .userId(userDTO.getUserId())
                .build();
        log.info("Creando user con UUID: {}", generatedId);
        return iUserServicePort.save(user);
    }
    @Override
    public Mono<Void> delete(String id) {
        log.info("Guardando user con ID: {}", id);
        return iUserServicePort.delete(id);
    }

}
