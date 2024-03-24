package com.apitesting.application.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apitesting.application.domain.User;
import com.apitesting.application.repository.UserRepository;
import com.apitesting.application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findById(Integer id) {
		return Optional.of(userRepository.findById(id)).get().orElse(null);
	}

}
