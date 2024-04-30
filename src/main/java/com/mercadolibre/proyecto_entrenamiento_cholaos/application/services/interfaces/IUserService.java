package com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.interfaces;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<UserDTO> getById(String id);
    Mono<Void> save(String id, UserDTO userDTO);
    Mono<Void> delete(String id);

}
