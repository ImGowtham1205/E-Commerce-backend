package com.example.ecommerce.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.razorpay.RazorpayException;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> loginException(){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Incorrect mail id or password");
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> imageUploadException(){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product image");
	}
	
	@ExceptionHandler(RazorpayException.class)
	public ResponseEntity<String> paymentException(){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("razorpay service not available currently...");
	}
	
}
