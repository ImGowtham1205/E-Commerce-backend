package com.example.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Users;

@Service
public class AuthenticationService {
	
	private AuthenticationManager manager;
	
	public AuthenticationService(AuthenticationManager manager) {
		this.manager=manager;
	}
	
	public ResponseEntity<String> verify(Users user) {
		try {
			Authentication auth =  
				manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			if (auth.isAuthenticated())
				return ResponseEntity.ok("Authentication Successfully");
				
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Incorrect mail id or password");
		} 
		catch(AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Incorrect mail id or password");
		}		
	}	
}
