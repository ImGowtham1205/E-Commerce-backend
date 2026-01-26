package com.example.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Products;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.OrdersRepo;

@Service
public class OrderService {
	
	private OrdersRepo orderrepo;
	private MailService mailservice;
	private ProductService productservice;
	
	public OrderService(OrdersRepo orderrepo,MailService mailservice,ProductService productservice) {
		this.mailservice =mailservice;
		this.orderrepo = orderrepo;
		this.productservice = productservice;
	}
	
	public ResponseEntity<String> placeOrder(Orders order,Products product,Users user){
		orderrepo.save(order);
		productservice.updateStock(product);
		mailservice.orderConfirmationMail(product, user, order);
		return ResponseEntity.ok("Product purchased successfully");
	}
	
	public List<Orders> fetchOrderByUser(Users user){
		return orderrepo.findByUserid(user.getId());
	}
}
