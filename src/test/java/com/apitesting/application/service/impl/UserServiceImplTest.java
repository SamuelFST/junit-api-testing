package com.apitesting.application.service.impl;

import com.apitesting.application.config.exception.service.ObjectNotFoundException;
import com.apitesting.application.domain.User;
import com.apitesting.application.dto.UserDTO;
import com.apitesting.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Optionals;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

    private static final Integer ID = 1;
    private static final String NAME = "Bob";
    public static final String EMAIL = "bob@mail.com";
    public static final String PASSWORD = "12345";
    String OBJECT_NOT_FOUND_MESSAGE = "Object not found";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDto;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startUser();
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();
    }

    @Test
    void whenFindByIdThenReturnAnUser() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdNotFoundThenReturnException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        try {
            userService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}