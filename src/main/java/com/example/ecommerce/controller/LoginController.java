package com.example.ecommerce.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.JwtService;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class LoginController {
	
	private AuthenticationService authservice;
	private JwtService jwtservice;
	
	public LoginController(AuthenticationService authservice,JwtService jwtservice) {
		this.authservice = authservice;
		this.jwtservice = jwtservice;
	}
	
		@PostMapping("/login")
		public ResponseEntity<?> login(@RequestBody Users user) throws AuthenticationException{
			Authentication auth = authservice.verify(user);
			if(auth.isAuthenticated()) {
				UserDetails userdetails = (UserDetails) auth.getPrincipal();
				String email = user.getEmail();
				String role = userdetails.getAuthorities().iterator().next().getAuthority();
				String token = jwtservice.generateToken(email,role);
				return ResponseEntity.ok(Map.of("token",token,"role",role));
			}	
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password"));
		}
}
