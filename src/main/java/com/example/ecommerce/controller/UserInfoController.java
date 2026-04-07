package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Admins;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserInfoController {
	
	private JwtService jwtservice;
	private UsersService userservice;
	
	@GetMapping("/api/user/userinfo")
	public Users userInfo(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = userservice.getUser(email);
		return user;
	}
	
	@GetMapping("/api/admin/admininfo")
	public Admins adminInfo(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Admins admin = userservice.getAdmin(email);
		return admin;
	}
}
