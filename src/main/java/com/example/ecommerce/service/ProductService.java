package com.example.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.repository.ProductRepo;

@Service
public class ProductService {
	
	private ProductRepo productrepo;
	
	public ProductService(ProductRepo productrepo) {
		this.productrepo = productrepo;
	}
	
	public ResponseEntity<String> addProduct(Products product) {
		productrepo.save(product);
		return ResponseEntity.status(HttpStatus.OK).body("Product added successfully");
	}
	
}
