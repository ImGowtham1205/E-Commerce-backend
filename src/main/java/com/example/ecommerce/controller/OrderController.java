package com.example.ecommerce.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Products;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.CommentService;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PasswordService;
import com.example.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderController {
	
	private CommentService commentservice;
	private OrderService orderservice;
	private ProductService productservice;
	private JwtService jwtservice;
	private PasswordService passwordservice;
	
	public OrderController(CommentService commentservice,OrderService orderservice,
			ProductService productservice,JwtService jwtservice,PasswordService passwordservice) {
		this.commentservice = commentservice;
		this.orderservice = orderservice;
		this.productservice = productservice;
		this.jwtservice = jwtservice;
		this.passwordservice = passwordservice;
	}
	
	@PostMapping("/api/user/purchase/{productid}")
	public ResponseEntity<String> purchaseProduct(@PathVariable long productid,
			@RequestBody Map<String,Long> body){
		long userid = body.get("userid");
		Users user = commentservice.getUserById(userid);
		Orders order = new Orders();
		
		order.setUserid(userid);
		order.setProductid(productid);
		order.setOrderdate(LocalDate.now());
		order.setOrdertime(LocalTime.now());
		
		Products product = productservice.getProductById(productid);
		product.setStock(product.getStock()-1);
		
		ResponseEntity<String> status = orderservice.placeOrder(order,product,user);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
	
	@GetMapping("/api/user/fetchorder")
	public List<Orders> fetchOrder(HttpServletRequest request){
		String token = jwtservice.getToken(request);
		String email = jwtservice.extractEmail(token);
		Users user = passwordservice.getUser(email);
		List<Orders> userorder = orderservice.fetchOrderByUser(user);
		return userorder;
	}
	
	@GetMapping("/api/admin/fetchorders")
	public List<Orders> fetchOrders(){
		return orderservice.fetchOrders();
	}
	
	@DeleteMapping("/api/user/cancelorder/{orderid}")
	public ResponseEntity<String> cancelOrder(@PathVariable long orderid){
		ResponseEntity<String> status = orderservice.cancelOrder(orderid);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
}