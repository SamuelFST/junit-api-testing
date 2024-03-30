package com.apitesting.application.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apitesting.application.domain.User;
import com.apitesting.application.repository.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired
	private UserRepository userRepository;

	@Bean
	public CommandLineRunner startDB() {
		User user1 = new User(null, "Bob", "bob@mail.com", "12345");
		User user2 = new User(null, "Alex", "alex@mail.com", "12345");
		User user3 = new User(null, "Maria", "maria@mail.com", "12345");

		userRepository.saveAll(List.of(user1, user2, user3));

		return args -> {
		};
	}

}
