package com.apitesting.application.service;

import java.util.List;

import com.apitesting.application.domain.User;
import com.apitesting.application.dto.UserDTO;

public interface UserService {
	List<User> findAll();

	User findById(Integer id);

	User create(UserDTO userDto);
}
