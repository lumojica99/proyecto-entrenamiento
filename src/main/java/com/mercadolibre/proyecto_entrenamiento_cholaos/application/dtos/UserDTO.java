package com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class UserDTO {
    private String id;
    private String itemId;
    private String userId;
    private String siteId;
}