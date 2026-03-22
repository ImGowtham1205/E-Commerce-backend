package com.example.ecommerce.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Users;

@Service
public class AuthenticationService {

	private AuthenticationManager manager;
	private UsersService usersservice;

	public AuthenticationService(AuthenticationManager manager, UsersService usersservice) {
		this.manager = manager;
		this.usersservice = usersservice;
	}

	public UserDetails loadUser(String email) {
		return usersservice.loadUserByUsername(email);
	}

	public Authentication verify(Users user) throws AuthenticationException {
		return manager.authenticate
				(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
	}
}