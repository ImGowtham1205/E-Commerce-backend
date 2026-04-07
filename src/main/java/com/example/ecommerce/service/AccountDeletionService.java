package com.example.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.model.Admins;
import com.example.ecommerce.model.BlackListedToken;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.AdminRepo;
import com.example.ecommerce.repository.BlackListTokenRepo;
import com.example.ecommerce.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountDeletionService {
	
	private UserRepo userrepo;
	private AdminRepo adminrepo;
	private PasswordService passwordservice;
	private BlackListTokenRepo blacklisttokenrepo;
	private JwtService jwtservice;
	private MailService mailsevice;
	private UserCleanUpService usercleanservice;
		
	@Transactional
	public ResponseEntity<String> userAccountDeletion(String email,String password,String token){
		if(!userrepo.existsByEmail(email)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		Users user = userrepo.findByEmail(email);
		if(passwordservice.checkCurrentPassword(user, password)) {
			userrepo.deleteByEmail(email);
			blackListToken(token);
			usercleanservice.cleanupUserData(user.getId());
			mailsevice.userAccountDeletionMail(user);
			return ResponseEntity.ok("Account Deleted Successfully");
		}
		else 
			return ResponseEntity.badRequest().body("Current Password doesn't match");
	}
	
	@Transactional
	public ResponseEntity<String> adminAccountDeletion(String email,String password,String token){
		if(!adminrepo.existsByEmail(email))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		Admins admin =adminrepo.findByEmail(email);
		
		if(passwordservice.checkCurrentPassword(admin, password)) {
			adminrepo.deleteByEmail(email);
			blackListToken(token);
			mailsevice.adminAccountDeletionMail(admin);
			return ResponseEntity.ok("Account Deleted Successfully");
		}
		else 
			return ResponseEntity.badRequest().body("Current Password doesn't match");
	}
	
	public void blackListToken(String token) {
		BlackListedToken b = new BlackListedToken();
		b.setToken(token);
		b.setExiprytime(jwtservice.extractExpiration(token));
		blacklisttokenrepo.save(b);
	}
}
