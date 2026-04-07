package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.service.AccountDeletionService;
import com.example.ecommerce.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccountDeletionController {
	
	private JwtService jwtservice;
	private AccountDeletionService accountdeletionservice;
	
	@DeleteMapping("/api/user/accountdeletion")
	public ResponseEntity<String> userAccountDelete(HttpServletRequest request,@RequestBody String password){
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		ResponseEntity<String> status = accountdeletionservice.userAccountDeletion(email,password,token);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
	
	@DeleteMapping("/api/admin/accountdeletion")
	public ResponseEntity<String> adminAccountDelete(HttpServletRequest request,@RequestBody String password){
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		ResponseEntity<String> status = accountdeletionservice.adminAccountDeletion(email,password,token);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
}
