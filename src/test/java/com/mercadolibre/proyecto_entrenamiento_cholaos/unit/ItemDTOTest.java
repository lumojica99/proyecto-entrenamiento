package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.ItemDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemDTOTest {

    @Test
    public void testNoArgsConstructor() {
        ItemDTO item = new ItemDTO();
        assertEquals(null, item.getId());
        assertEquals(null, item.getItemId());
        assertEquals(null, item.getUserId());
        assertEquals(null, item.getSiteId());
    }

    @Test
    public void testAllArgsConstructor() {
        ItemDTO item = new ItemDTO("1", "MLA123", "user1", "MLA");
        assertEquals("1", item.getId());
        assertEquals("MLA123", item.getItemId());
        assertEquals("user1", item.getUserId());
        assertEquals("MLA", item.getSiteId());
    }

    @Test
    public void testSettersAndGetters() {
        ItemDTO item = new ItemDTO();
        item.setId("1");
        item.setItemId("MLA123");
        item.setUserId("user1");
        item.setSiteId("MLA");

        assertEquals("1", item.getId());
        assertEquals("MLA123", item.getItemId());
        assertEquals("user1", item.getUserId());
        assertEquals("MLA", item.getSiteId());
    }

    @Test
    public void testBuilder() {
        ItemDTO item = ItemDTO.builder()
                .id("1")
                .itemId("MLA123")
                .userId("user1")
                .siteId("MLA")
                .build();

        assertEquals("1", item.getId());
        assertEquals("MLA123", item.getItemId());
        assertEquals("user1", item.getUserId());
        assertEquals("MLA", item.getSiteId());
    }
}
