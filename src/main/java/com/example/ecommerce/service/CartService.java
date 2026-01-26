package com.example.ecommerce.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.repository.CartRepo;

@Service
public class CartService {
	
	private CartRepo cartrepo;
	
	public CartService(CartRepo cartrepo) {
		this.cartrepo = cartrepo;
	}
	
	public ResponseEntity<String> addCart(Cart cart){
		cartrepo.save(cart);
		return ResponseEntity.ok("Product added to cart successfully");
	}
	
	public List<Cart> fetchCartProduct(long userid){
		return cartrepo.findByUserId(userid);
	}
	
	public void deleteCartItemById(ObjectId id){
		cartrepo.deleteById(id);
	}
	
	public void updateQuantity(Cart cart) {
		cartrepo.save(cart);
	}
}
