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
public class HomeController {
	
	private JwtService jwtservice;
	private PasswordService passservice;
	
	public HomeController(JwtService jwtservice,PasswordService passservice) {
		this.jwtservice = jwtservice;
		this.passservice = passservice;
	}
	
	@GetMapping("/api/user/home")
	public String userGreet(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		if(token == null)
			return null;
		String email = jwtservice.extractEmail(token);
		Users user = passservice.getUser(email);
		return "Welcome , "+user.getName();
	}
	
	@GetMapping("/api/admin/home")
	public String adminGreet(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		if(token == null)
			return null;
		String email = jwtservice.extractEmail(token);
		Admins admin = passservice.getAdmin(email);
		return "Welcome , "+admin.getAdminName();
	}
}	