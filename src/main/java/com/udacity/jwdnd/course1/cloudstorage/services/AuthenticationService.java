package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;

@Service
public class AuthenticationService implements AuthenticationProvider {

	private UserMapper userMapper;
	private HashService hashService;
	
	public AuthenticationService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// retrieve username, password from input
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		// get user from db
		User user = this.userMapper.getUser(username);
		if (user != null) {
			
			String hashedPassword = hashService.getHashedValue(password, user.getSalt());
			// comparing the hashedPassword and stored password
			if (user.getPassword().equals(hashedPassword))
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
