package com.example.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.projection.ProductView;
import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.repository.CommentRepo;
import com.example.ecommerce.repository.ProductRepo;

@Service
public class ProductService {
	
	private ProductRepo productrepo;
	private CartRepo cartrepo;
	private CommentRepo commentrepo;
	
	public ProductService(ProductRepo productrepo,CartRepo cartrepo,CommentRepo commentrepo) {
		this.productrepo = productrepo;
		this.cartrepo = cartrepo;
		this.commentrepo = commentrepo;
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
	
	public ResponseEntity<String> updateProduct(Products product){
		productrepo.save(product);
		return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully");
	}
	
	public ResponseEntity<String> deleteProduct(long id){
		cartrepo.deleteByProductId(id);
		commentrepo.deleteByproductid(id);
		productrepo.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
	}
	
	public List<ProductView> fetchAllProducts(){
		return productrepo.findAllProducts();
	}
	
	public void updateStock(Products product){
		productrepo.save(product);
	}
}
