package com.example.ecommerce.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.repository.CommentRepo;

@Service
public class UserCleanUpService {
	
	private CommentRepo commentrepo;
	private CartRepo cartrepo;
	
	public UserCleanUpService(CommentRepo commentrepo,CartRepo cartrepo) {
		this.cartrepo = cartrepo;
		this.commentrepo = commentrepo;
	}
	
	@Async
	public void cleanupUserData(Long userId) {
	    commentrepo.deleteByUserid(userId);
	    cartrepo.deleteByUserId(userId);
	}
	
}
