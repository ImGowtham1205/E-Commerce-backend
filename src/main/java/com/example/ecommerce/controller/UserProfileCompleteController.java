package com.example.ecommerce.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserProfileCompleteController {

	private JwtService jwtservice;
	private UsersService userservice;
	private PasswordEncoder encorder;
	
	public UserProfileCompleteController(JwtService jwtservice,UsersService userservice
			,PasswordEncoder encorder) {
		this.jwtservice = jwtservice;
		this.userservice = userservice;
		this.encorder = encorder;
	}
	
	@PutMapping("/api/user/complete-profile")
	public ResponseEntity<String> profileComplete(@RequestBody Map<String,String> profile
			,HttpServletRequest request){
		String password = profile.get("password");
		String address = profile.get("address");
		String phoneno = profile.get("phoneno");
		
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = userservice.getUser(email);
		
		user.setAddress(address);
		user.setPhoneno(phoneno);
		user.setPassword(encorder.encode(password));
		user.setProfileCompleted(false);
		userservice.updateUserProfile(user);
		return ResponseEntity.ok("Your Profile Completed Successfully..");
	}
	
}
