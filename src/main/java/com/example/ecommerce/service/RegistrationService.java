package com.example.ecommerce.service;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private UserRepo userrepo;
	private MailService mailservice;
	
	public void registerUser(Users user) {
		userrepo.save(user);
		mailservice.accountCreationMail(user);
	}
	
	public boolean existsMail(String mail) {
		return userrepo.existsByEmail(mail);
	}
	
	public boolean existsPhoneNo(String phoneno) {
		return userrepo.existsByPhoneno(phoneno);
	}
}
