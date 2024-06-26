package com.apitesting.application.config.exception.resource;

import com.apitesting.application.config.exception.service.DataIntegrityViolationException;
import com.apitesting.application.config.exception.service.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    private static final String OBJECT_NOT_FOUND = "Object not found";
    private static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    private static final String E_MAIL_ALREADY_IN_USE = "E-mail already in use";
    private static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFoundException(
                new ObjectNotFoundException(OBJECT_NOT_FOUND),
                new MockHttpServletRequest()
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(NOT_FOUND, response.getStatusCode().value());
        assertEquals(NOT_FOUND, response.getBody().getStatus());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJECT_NOT_FOUND, response.getBody().getError());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolationException(
                new DataIntegrityViolationException(E_MAIL_ALREADY_IN_USE),
                new MockHttpServletRequest()
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(BAD_REQUEST, response.getStatusCode().value());
        assertEquals(BAD_REQUEST, response.getBody().getStatus());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(E_MAIL_ALREADY_IN_USE, response.getBody().getError());
    }
}