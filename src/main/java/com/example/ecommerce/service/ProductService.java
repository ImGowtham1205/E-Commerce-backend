package com.example.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.projection.ProductView;
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
	
	public List<ProductView> fetchProductsByCategory(String category){
		return productrepo.findByCategory(category);
	}
	
	public Products getProductById(long id) {
		return productrepo.findById(id).orElse(null);
	}
}
