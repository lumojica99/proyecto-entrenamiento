package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.dto.GenericKvsDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericKvsDTOTest {

    @Test
    public void testNoArgsConstructor() {
        GenericKvsDTO dto = new GenericKvsDTO();
        assertEquals(null, dto.getId());
        assertEquals(null, dto.getItemId());
        assertEquals(null, dto.getUserId());
        assertEquals(null, dto.getSiteId());
    }

    @Test
    public void testAllArgsConstructor() {
        GenericKvsDTO dto = new GenericKvsDTO("1", "Item123", "User123", "MLA");
        assertEquals("1", dto.getId());
        assertEquals("Item123", dto.getItemId());
        assertEquals("User123", dto.getUserId());
        assertEquals("MLA", dto.getSiteId());
    }

    @Test
    public void testSettersAndGetters() {
        GenericKvsDTO dto = new GenericKvsDTO();
        dto.setId("1");
        dto.setItemId("Item123");
        dto.setUserId("User123");
        dto.setSiteId("MLA");

        assertEquals("1", dto.getId());
        assertEquals("Item123", dto.getItemId());
        assertEquals("User123", dto.getUserId());
        assertEquals("MLA", dto.getSiteId());
    }

    @Test
    public void testBuilder() {
        GenericKvsDTO dto = GenericKvsDTO.builder()
                .id("1")
                .itemId("Item123")
                .userId("User123")
                .siteId("MLA")
                .build();

        assertEquals("1", dto.getId());
        assertEquals("Item123", dto.getItemId());
        assertEquals("User123", dto.getUserId());
        assertEquals("MLA", dto.getSiteId());
    }
}