package com.example.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.projection.ProductView;
import com.example.ecommerce.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class ProductController {
	
	private ProductService productservice;
	
	public ProductController(ProductService productservice) {
		this.productservice = productservice;
	}
	
	@PostMapping("/api/admin/addproduct")
	public ResponseEntity<String> addProducts(@RequestPart Products product,
			@RequestPart (required = false) MultipartFile file){
		
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
	
	@PutMapping("/api/admin/updateproduct")
	public ResponseEntity<String> updateProduct(@RequestPart Products product , 
			@RequestPart(required = false) MultipartFile file){
		ResponseEntity<String> status = null;
		try {
			if(file != null && !file.isEmpty()) {
				product.setImagename(file.getOriginalFilename());
				product.setImagetype(file.getContentType());
				product.setImagedata(file.getBytes());
			}			
			status = productservice.updateProduct(product);
		}catch(IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Failed to Update product");
		}
			
		return ResponseEntity.ok(status.getBody());
	}
	
	@DeleteMapping("/api/admin/deleteproduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable long id){
		ResponseEntity<String> status = productservice.deleteProduct(id);
		return ResponseEntity.ok(status.getBody());
	}
	
	@GetMapping("/api/products/{category}")
	public List<ProductView> fetchProductByCategory(@PathVariable String category){
		return productservice.fetchProductsByCategory(category);
	}
	
	@GetMapping("/api/products/details/{id}")
	public Products fetchProductById(@PathVariable long id) {
		return productservice.getProductById(id);
	}
	
	@GetMapping("/api/products/image/{id}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable long id){
		Products product = productservice.getProductById(id);
		byte[] imagedata = product.getImagedata();
		return ResponseEntity.ok()
				.contentType(MediaType.valueOf(product.getImagetype()))
				.body(imagedata);
	}
	
	@GetMapping("/api/products")
	public List<ProductView> getproducts(){
		return productservice.fetchAllProducts();
	}
}