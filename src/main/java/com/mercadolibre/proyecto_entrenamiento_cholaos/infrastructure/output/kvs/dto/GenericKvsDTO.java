package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericKvsDTO {
    private String id;
    private String itemId;
    private String userId;
    private String siteId;
}
