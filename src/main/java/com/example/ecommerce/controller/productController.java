package com.example.ecommerce.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class productController {
	
	private ProductService productservice;
	
	public productController(ProductService productservice) {
		this.productservice = productservice;
	}
	
	@PostMapping("/api/admin/addproduct")
	public ResponseEntity<String> addProducts(@RequestPart Products product,
			@RequestPart MultipartFile file){
		
		ResponseEntity<String> status = null;
		try {
			product.setImagename(file.getOriginalFilename());
			product.setImagetype(file.getContentType());
			product.setImagedata(file.getBytes());
			status = productservice.addProduct(product);
		}catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product");
		}
		
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
}
