package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.PasswordResetToken;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.PasswordResetTokenRepo;
import com.example.ecommerce.repository.UserRepo;

@Service
public class PasswordService {
	
	private UserRepo userrepo;
	private PasswordResetTokenRepo passwordrepo;
	private MailService mailservice;
	private PasswordEncoder encorder;
	
	public PasswordService(UserRepo userrepo,PasswordResetTokenRepo passwordrepo,
			MailService mailservice,PasswordEncoder encorder) {
		this.userrepo = userrepo;
		this.passwordrepo = passwordrepo;
		this.mailservice = mailservice;
		this.encorder = encorder;
	}
	
	public ResponseEntity<String> forgotPassword(String email) {
		String token = UUID.randomUUID().toString();
		Users user = userrepo.findByEmail(email);
		if(user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Enter Your Registered Email");
		
		PasswordResetToken prt = new PasswordResetToken();
		prt.setToken(token);
		prt.setUser(user);
		prt.setExpiryTime(LocalDateTime.now().plusMinutes(15));
		passwordrepo.save(prt);
		
		mailservice.forgotPasswordMail(user, token);
			
		return ResponseEntity.status(HttpStatus.OK).body("Mail Sent Successfully");
	}
	
	public PasswordResetToken checkToken(String token) {
		PasswordResetToken prt = passwordrepo.findByToken(token);
		if(prt == null || prt.isExpiry())
			return null;
		return prt;
	}
	
	public void changepassword(Users user) {
		userrepo.save(user);
	}
	
	public void deleteToken(PasswordResetToken prt) {
		int id = prt.getId();
		passwordrepo.deleteById(id);
	}
	
	public Users getUser(String email) {
		return userrepo.findByEmail(email);
	}
	
	public boolean checkCurrentPassword(Users user ,String currentPassword) {
		if(encorder.matches(currentPassword, user.getPassword()))
			return true;
		else 
			return false;
	}	
}
