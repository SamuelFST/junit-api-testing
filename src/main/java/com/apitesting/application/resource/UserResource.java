package com.apitesting.application.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apitesting.application.dto.UserDTO;
import com.apitesting.application.service.UserService;

@RestController
@RequestMapping(value = UserResource.USER_ENDPOINT)
public class UserResource {

	static final String USER_ENDPOINT = "/user";
	private static final String ID = "/{id}";

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok().body(userService.findAll().stream().map(user -> mapper.map(user, UserDTO.class))
				.collect(Collectors.toList()));
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer userId) {
		return ResponseEntity.ok().body(mapper.map(userService.findById(userId), UserDTO.class));
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDto) {
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
				.buildAndExpand(userService.create(userDto).getId()).toUri()).build();
	}

	@PutMapping(value = ID)
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDto) {
		userDto.setId(id);
		return ResponseEntity.ok().body(mapper.map(userService.update(userDto), UserDTO.class));
	}

	@DeleteMapping(value = ID)
	public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
