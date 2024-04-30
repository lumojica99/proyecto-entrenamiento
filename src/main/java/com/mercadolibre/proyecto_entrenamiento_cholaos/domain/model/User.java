package com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String itemId;
    private String siteId;
    private String userId;
}
