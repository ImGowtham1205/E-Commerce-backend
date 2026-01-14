package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Admins;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserInfoController {
	
	private JwtService jwtservice;
	private PasswordService passservice;
	
	public UserInfoController(JwtService jwtservice,PasswordService passservice) {
		this.jwtservice = jwtservice;
		this.passservice = passservice;
	}
	
	@GetMapping("/api/user/userinfo")
	public Users userInfo(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = passservice.getUser(email);
		return user;
	}
	
	@GetMapping("/api/admin/admininfo")
	public Admins adminInfo(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Admins admin = passservice.getAdmin(email);
		return admin;
	}
}
