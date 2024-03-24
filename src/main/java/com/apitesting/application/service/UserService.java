package com.apitesting.application.service;

import java.util.List;

import com.apitesting.application.domain.User;

public interface UserService {

	List<User> findAll();

	User findById(Integer id);
}
