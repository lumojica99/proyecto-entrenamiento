package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        assert context != null;
    }

    @Test
    public void mainMethodTest() {
        Application.main(new String[]{});
    }
}
