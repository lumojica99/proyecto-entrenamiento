package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.UserDTO;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.exceptions.UserServiceException;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.services.implementation.UserServiceImpl;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.api.IUserServicePort;
import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private IUserServicePort iServicePort;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void getById_WhenIdExists_ReturnsUserDTO() {
        String userId = "IdValido";
        User user = User.builder()
                .id("userId")
                .itemId("itemId")
                .siteId("siteId")
                .userId(userId)
                .build();
        IUserServicePort mockPort = mock(IUserServicePort.class);
        when(mockPort.getById(userId)).thenReturn(Mono.just(user));

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        Mono<UserDTO> result = userService.getById(userId);

        assertNotNull(result);
        UserDTO userDTO = result.block();
        assertNotNull(userDTO);
        assertEquals(userId, userDTO.getUserId());
    }

    @Test
    public void getById_WhenIdDoesNotExist_ReturnsEmptyMono() {
        String userId = "idInexistente";
        IUserServicePort mockPort = mock(IUserServicePort.class);
        when(mockPort.getById(userId)).thenReturn(Mono.empty());

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        Mono<UserDTO> result = userService.getById(userId);

        assertNotNull(result);
        assertNull(result.block());
    }

    @Test
    public void save_WhenValidSiteProvided_SavesUserAndReturnsVoid() {
        String itemId = "ItemIdValido";
        String siteId = "SiteIdValido";
        String userId = "UserIdValido";
        UserDTO userDTO = UserDTO.builder()
                .siteId(siteId)
                .userId(userId)
                .build();
        User user = User.builder()
                .id("IdGenerado")
                .itemId(itemId)
                .siteId(siteId)
                .userId(userId)
                .build();
        IUserServicePort mockPort = mock(IUserServicePort.class);
        when(mockPort.getById(userId)).thenReturn(Mono.empty());
        when(mockPort.save(any(User.class))).thenReturn(Mono.empty());

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        Mono<Void> result = userService.save(userId, userDTO);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    public void save_WhenInvalidSiteProvided_ThrowsUserServiceException() {

        String itemId = "itemIdValido";
        String invalidSiteId = "MLM";
        String userId = "userIdValido";
        UserDTO userDTO = UserDTO.builder()
                .siteId(invalidSiteId)
                .userId(userId)
                .build();
        IUserServicePort mockPort = mock(IUserServicePort.class);

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        assertThrows(UserServiceException.class, () -> userService.save(userId, userDTO));
    }

    @Test
    public void delete_WhenExistingUserIdProvided_DeletesUserAndReturnsVoid() {
        String userId = "UserIdValido";
        IUserServicePort mockPort = mock(IUserServicePort.class);
        when(mockPort.delete(userId)).thenReturn(Mono.empty());

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        Mono<Void> result = userService.delete(userId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    public void delete_WhenNonexistentUserIdProvided_ReturnsVoid() {
        String nonexistentUserId = "UserIdInexistente";
        IUserServicePort mockPort = mock(IUserServicePort.class);
        when(mockPort.delete(nonexistentUserId)).thenReturn(Mono.empty());

        UserServiceImpl userService = new UserServiceImpl(mockPort);

        Mono<Void> result = userService.delete(nonexistentUserId);

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
    }


}