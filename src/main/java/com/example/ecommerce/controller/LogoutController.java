package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.service.AccountDeletionService;
import com.example.ecommerce.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LogoutController {
	
	private JwtService jwtservice;
	private AccountDeletionService accountdeletionservice;
	
	@PostMapping("/api/user/logout")
	public void userLogout(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		accountdeletionservice.blackListToken(token);
	}
	
	@PostMapping("/api/admin/logout")
	public void adminLogout(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		accountdeletionservice.blackListToken(token);
	}
}
