package com.example.ecommerce.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CartController {
	
	private CartService cartservice;
	private JwtService jwtservice;
	private PasswordService passservice;
	
	public CartController(CartService cartservice,JwtService jwtservice,PasswordService passservice) {
		this.cartservice = cartservice;
		this.jwtservice = jwtservice;
		this.passservice = passservice;
	}
	
	@PostMapping("/api/user/addtocart")
	public ResponseEntity<String> addCart(@RequestBody Cart cart,HttpServletRequest request){
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = passservice.getUser(email);
		cart.setUserId(user.getId());
		ResponseEntity<String> status = cartservice.addCart(cart);
		return ResponseEntity.ok(status.getBody());
	}	
	
	@GetMapping("/api/user/getcartitem")
	public List<Cart> fetchCartItemsByUser(HttpServletRequest request){
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = passservice.getUser(email);
		long userid = user.getId();
		return cartservice.fetchCartProduct(userid);
	}
	
	@DeleteMapping("/api/user/deletecartitem")
	public ResponseEntity<String> deleteSelectedCartItem(@RequestBody String id){
		id = id.replace("\"", "").trim();
		ObjectId objectid = new ObjectId(id);
		cartservice.deleteCartItemById(objectid);
		return ResponseEntity.ok("Product removed from your cart successfully");
	}
	
	@PutMapping("/api/user/updatequantity")
	public ResponseEntity<String> updateQuantity(@RequestBody Cart cart){
		cart.setQuantity(cart.getQuantity());
		cartservice.updateQuantity(cart);
		return ResponseEntity.ok("Product quantity successfully updated to your cart");
	}
}
