package com.example.ecommerce.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.PasswordResetToken;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class PasswordController {
	
	private PasswordService passwordservice;
	private PasswordEncoder encorder;
	private JwtService jwtservice;
	
	public PasswordController(PasswordService passwordservice,PasswordEncoder encorder
			,JwtService jwtservice) {
		this.passwordservice = passwordservice;
		this.encorder = encorder;
		this.jwtservice = jwtservice;
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> body) {
		String email = body.get("email");
		ResponseEntity<String> status = passwordservice.forgotPassword(email);
		if(status.getStatusCode() == HttpStatus.NOT_FOUND)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status.getBody());
		
		return ResponseEntity.status(HttpStatus.OK).body(status.getBody());
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String,String> body){
		String token = body.get("token");
		String password = body.get("password");
		PasswordResetToken prt = passwordservice.checkToken(token);
		if(prt == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expiry link");
		Users user = prt.getUser();
		user.setPassword(encorder.encode(password));
		passwordservice.changepassword(user);
		passwordservice.deleteToken(prt);
		return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
	}
	
	@PutMapping("/api/changepassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String,String> body,
			HttpServletRequest request){
		
		String currentPassword = body.get("currentpassword");
		String newPassword = body.get("newpassword");
				
		String token = jwtservice.getToken(request);
				
		if(token == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
					body("Unauthorized");
		
		String email = jwtservice.extractEmail(token);
		
		Users user = passwordservice.getUser(email);
		
		if(!(passwordservice.checkCurrentPassword(user, currentPassword)))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password doesn't match");
		
		user.setPassword(encorder.encode(newPassword));
		passwordservice.changepassword(user);
		return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
	}
}
