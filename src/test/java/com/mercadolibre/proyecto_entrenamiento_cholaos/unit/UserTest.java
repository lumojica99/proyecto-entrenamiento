package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertEquals(null, user.getId());
        assertEquals(null, user.getItemId());
        assertEquals(null, user.getSiteId());
        assertEquals(null, user.getUserId());
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User("1", "Item123", "MLA", "User123");
        assertEquals("1", user.getId());
        assertEquals("Item123", user.getItemId());
        assertEquals("MLA", user.getSiteId());
        assertEquals("User123", user.getUserId());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setId("1");
        user.setItemId("Item123");
        user.setSiteId("MLA");
        user.setUserId("User123");

        assertEquals("1", user.getId());
        assertEquals("Item123", user.getItemId());
        assertEquals("MLA", user.getSiteId());
        assertEquals("User123", user.getUserId());
    }

    @Test
    public void testBuilder() {
        User user = User.builder()
                .id("1")
                .itemId("Item123")
                .siteId("MLA")
                .userId("User123")
                .build();

        assertEquals("1", user.getId());
        assertEquals("Item123", user.getItemId());
        assertEquals("MLA", user.getSiteId());
        assertEquals("User123", user.getUserId());
    }
}
