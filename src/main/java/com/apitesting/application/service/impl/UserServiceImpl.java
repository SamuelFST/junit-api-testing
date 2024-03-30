package com.apitesting.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apitesting.application.config.exception.service.DataIntegrityViolationException;
import com.apitesting.application.config.exception.service.ObjectNotFoundException;
import com.apitesting.application.domain.User;
import com.apitesting.application.dto.UserDTO;
import com.apitesting.application.repository.UserRepository;
import com.apitesting.application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Integer id) {
		return Optional.of(userRepository.findById(id)).get()
				.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
	}

	@Override
	public User create(UserDTO userDto) {
		this.validateEmail(userDto);
		return userRepository.save(mapper.map(userDto, User.class));
	}

	@Override
	public User update(UserDTO userDto) {
		this.validateEmail(userDto);
		return userRepository.save(mapper.map(userDto, User.class));
	}

	private void validateEmail(UserDTO userDto) {
		Optional<User> user = userRepository.findByEmail(userDto.getEmail());

		if (user.isPresent() && !user.get().getId().equals(userDto.getId())) {
			throw new DataIntegrityViolationException("E-mail already in use");
		}
	}

}
