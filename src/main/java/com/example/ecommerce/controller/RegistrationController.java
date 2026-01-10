package com.example.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.RegistrationService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {
	
	private PasswordEncoder encorder;
	private RegistrationService registrationservice;
	public RegistrationController(PasswordEncoder encorder,RegistrationService registrationservice) {
		this.encorder=encorder;
		this.registrationservice=registrationservice;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Users user) {
		
		if(registrationservice.existsMail(user.getEmail()))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
		
		else if(registrationservice.existsPhoneNo(user.getPhoneno()))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already registered");
		
		user.setPassword(encorder.encode(user.getPassword()));
		registrationservice.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Account Created Successfully");
	}
}
