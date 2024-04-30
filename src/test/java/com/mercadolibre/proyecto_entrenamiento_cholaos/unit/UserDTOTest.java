package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDTOTest {

    @Test
    public void testNoArgsConstructor() {
        UserDTO user = new UserDTO();
        assertEquals(null, user.getId());
        assertEquals(null, user.getItemId());
        assertEquals(null, user.getUserId());
        assertEquals(null, user.getSiteId());
    }

    @Test
    public void testAllArgsConstructor() {
        UserDTO user = new UserDTO("1", "MLA123", "user1", "MLA");
        assertEquals("1", user.getId());
        assertEquals("MLA123", user.getItemId());
        assertEquals("user1", user.getUserId());
        assertEquals("MLA", user.getSiteId());
    }

    @Test
    public void testSettersAndGetters() {
        UserDTO user = new UserDTO();
        user.setId("1");
        user.setItemId("MLA123");
        user.setUserId("user1");
        user.setSiteId("MLA");

        assertEquals("1", user.getId());
        assertEquals("MLA123", user.getItemId());
        assertEquals("user1", user.getUserId());
        assertEquals("MLA", user.getSiteId());
    }

    @Test
    public void testBuilder() {
        UserDTO user = UserDTO.builder()
                .id("1")
                .itemId("MLA123")
                .userId("user1")
                .siteId("MLA")
                .build();

        assertEquals("1", user.getId());
        assertEquals("MLA123", user.getItemId());
        assertEquals("user1", user.getUserId());
        assertEquals("MLA", user.getSiteId());
    }
}
