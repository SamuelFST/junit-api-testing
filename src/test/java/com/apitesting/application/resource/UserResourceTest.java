package com.apitesting.application.resource;

import com.apitesting.application.domain.User;
import com.apitesting.application.dto.UserDTO;
import com.apitesting.application.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UserResourceTest {

    private static final int OK = HttpStatus.OK.value();
    private static final Integer ID = 1;
    private static final String NAME = "Bob";
    public static final String EMAIL = "bob@mail.com";
    public static final String PASSWORD = "12345";
    private static final int INDEX = 0;

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ModelMapper mapper;

    private User user = new User();
    private UserDTO userDto = new UserDTO();

    @BeforeEach
    void setUp() {
        openMocks(this);
        startUser();
    }

    @Test
    void whenFindAllThenReturnListOfUserDto() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDto);

        ResponseEntity<List<UserDTO>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(OK, response.getStatusCode().value());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDto);

        ResponseEntity<UserDTO> response = userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(OK, response.getStatusCode().value());
    }

    @Test
    void whenCreateUserThenReturnCreated() {
        when(userService.create(any())).thenReturn(user);
        String createdResourceLocation = "http://localhost/1";

        ResponseEntity<UserDTO> response = userResource.create(userDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNull(response.getBody());
        assertNotNull(response.getHeaders().get("Location"));
        assertEquals(createdResourceLocation, response.getHeaders().get("Location").get(INDEX));
    }

    @Test
    void whenUpdateUserThenReturnSuccess() {
        when(userService.update(userDto)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDto);

        ResponseEntity<UserDTO> response = userResource.update(ID, userDto);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(OK, response.getStatusCode().value());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }

    @Test
    void whenDeleteUserThenReturnSuccess() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<UserDTO> response = userResource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
        verify(userService, times(1)).delete(anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}