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
public class HomeController {
	
	private JwtService jwtservice;
	private UsersService userservice;
	
	@GetMapping("/api/user/home")
	public String userGreet(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		if(token == null)
			return null;
		String email = jwtservice.extractEmail(token);
		Users user = userservice.getUser(email);
		return "Welcome , "+user.getName();
	}
	
	@GetMapping("/api/admin/home")
	public String adminGreet(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		if(token == null)
			return null;
		String email = jwtservice.extractEmail(token);
		Admins admin = userservice.getAdmin(email);
		return "Welcome , "+admin.getAdminName();
	}
}	