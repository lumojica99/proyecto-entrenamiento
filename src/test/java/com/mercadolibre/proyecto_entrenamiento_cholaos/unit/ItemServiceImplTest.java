package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation.ItemServiceImpl;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IItemServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private IItemServicePort itemServicePort;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void testInjectionViaReflection() throws NoSuchFieldException, IllegalAccessException {
        Field field = ItemServiceImpl.class.getDeclaredField("iitemServicePort");
        field.setAccessible(true);
        IItemServicePort injectedPort = (IItemServicePort) field.get(itemService);
        assertSame(itemServicePort, injectedPort, "El puerto del servicio del servicio debe ser el mismo que el mock");
    }
    }
