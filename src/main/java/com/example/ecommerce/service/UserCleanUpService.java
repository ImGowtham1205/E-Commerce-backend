package com.example.ecommerce.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.repository.CommentRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserCleanUpService {
	
	private CommentRepo commentrepo;
	private CartRepo cartrepo;
		
	@Async
	public void cleanupUserData(Long userId) {
	    commentrepo.deleteByUserid(userId);
	    cartrepo.deleteByUserId(userId);
	}
	
}
