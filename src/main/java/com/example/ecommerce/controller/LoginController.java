package com.example.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.JwtService;

import jakarta.servlet.http.HttpServletResponse;

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
		public ResponseEntity<String> login(@RequestBody Users user,HttpServletResponse response) {
			ResponseEntity<String> status = authservice.verify(user);
			if(status.getStatusCode() == HttpStatus.OK) {
				String email = user.getEmail();
				String token = jwtservice.generateToken(email);
				return ResponseEntity.ok(token);
			}	
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status.getBody());
		}
}
