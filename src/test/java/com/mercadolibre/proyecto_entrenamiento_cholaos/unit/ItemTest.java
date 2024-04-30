package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    public void testNoArgsConstructor() {
        Item item = new Item();
        assertEquals(null, item.getId());
        assertEquals(null, item.getItemId());
        assertEquals(null, item.getSiteId());
        assertEquals(null, item.getUserId());
    }

    @Test
    public void testAllArgsConstructor() {
        Item item = new Item("1", "Item123", "MLA", "User123");
        assertEquals("1", item.getId());
        assertEquals("Item123", item.getItemId());
        assertEquals("MLA", item.getSiteId());
        assertEquals("User123", item.getUserId());
    }

    @Test
    public void testSettersAndGetters() {
        Item item = new Item();
        item.setId("1");
        item.setItemId("Item123");
        item.setSiteId("MLA");
        item.setUserId("User123");

        assertEquals("1", item.getId());
        assertEquals("Item123", item.getItemId());
        assertEquals("MLA", item.getSiteId());
        assertEquals("User123", item.getUserId());
    }

    @Test
    public void testBuilder() {
        Item item = Item.builder()
                .id("1")
                .itemId("Item123")
                .siteId("MLA")
                .userId("User123")
                .build();

        assertEquals("1", item.getId());
        assertEquals("Item123", item.getItemId());
        assertEquals("MLA", item.getSiteId());
        assertEquals("User123", item.getUserId());
    }
}
