package com.apitesting.application.config.exception.resource;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apitesting.application.config.exception.service.DataIntegrityViolationException;
import com.apitesting.application.config.exception.service.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException exception,
			HttpServletRequest request) {
		final int NOT_FOUND = HttpStatus.NOT_FOUND.value();

		StandardError error = new StandardError(LocalDateTime.now(), NOT_FOUND, exception.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException exception,
			HttpServletRequest request) {
		final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

		StandardError error = new StandardError(LocalDateTime.now(), BAD_REQUEST, exception.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(BAD_REQUEST).body(error);
	}
}