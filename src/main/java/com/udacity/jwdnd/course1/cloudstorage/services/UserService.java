package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;

@Service
public class UserService {

	private HashService hashService;
	private UserMapper userMapper;

	// Use constructor injection
	public UserService(HashService hashService, UserMapper userMapper) {
		this.hashService = hashService;
		this.userMapper = userMapper;
	}

	public User getUser(String username) {
		return userMapper.getUser(username);
	}
	
	public boolean isUsernameAvailable(String username) {
		return getUser(username) == null;
	}
	
	public int createUser(User user) {

		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insertUser(
				new User(user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
	}
}
