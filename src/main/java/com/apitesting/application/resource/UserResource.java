package com.apitesting.application.resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apitesting.application.dto.UserDTO;
import com.apitesting.application.service.UserService;

@RestController
@RequestMapping(value = UserResource.USER_ENDPOINT)
public class UserResource {

	static final String USER_ENDPOINT = "/user";

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer userId) {
		return ResponseEntity.ok().body(mapper.map(userService.findById(userId), UserDTO.class));
	}
}
