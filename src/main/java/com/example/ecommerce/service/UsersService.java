package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Admins;
import com.example.ecommerce.model.UserPrincipal;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.AdminRepo;
import com.example.ecommerce.repository.UserRepo;

@Service
public class UsersService implements UserDetailsService{

	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private AdminRepo adminrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Users user = userrepo.findByEmail(email);
		if(user != null)
			return new UserPrincipal(user);
		
		Admins admin = adminrepo.findByEmail(email);
		if(admin != null)
			return new UserPrincipal(admin);
		
		throw new UsernameNotFoundException("User Not Found");
	}
}
