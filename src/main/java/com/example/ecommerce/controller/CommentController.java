package com.example.ecommerce.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Comment;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.CommentService;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {
	
	private JwtService jwtservice;
	private PasswordService passservice;
	private CommentService commentservice;
	
	public CommentController(JwtService jwtservice,PasswordService passservice
			,CommentService commentservice) {
		this.jwtservice = jwtservice;
		this.passservice = passservice;
		this.commentservice = commentservice;
	}
	
	@PostMapping("/api/user/addcomment")
	public ResponseEntity<String> addComment(@RequestBody Comment cmt){
		Users user = commentservice.getUserById(cmt.getUserid());
		cmt.setUsername(user.getName());
		ResponseEntity<String> status = commentservice.addComment(cmt);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
	
	@PutMapping("/api/user/updatecomment")
	public ResponseEntity<String> updateComment(@RequestBody Comment cmt){
		Users user = commentservice.getUserById(cmt.getUserid());
		cmt.setUsername(user.getName());
		cmt.setReview(cmt.getReview());
		ResponseEntity<String> status = commentservice.updateComment(cmt);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
	
	@DeleteMapping("/api/user/deletecomment/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable String id){
		id = id.replace("\"", "").trim();
		ObjectId objectid = new ObjectId(id);
		ResponseEntity<String> status = commentservice.deleteComment(objectid);
		return ResponseEntity.status(status.getStatusCode()).body(status.getBody());
	}
	
	@GetMapping("/api/user/commentcount/{productid}")
	public ResponseEntity<Long> getCommentCount(@PathVariable long productid){
		long count = commentservice.getCommentCountForProduct(productid);
		return ResponseEntity.ok(count);
	}
	
	@GetMapping("/api/user/getcomments/{productid}")
	public List<Comment> getCommentsForproduct(@PathVariable long productid){
		return commentservice.getCommentForproduct(productid);
	}
	
	@GetMapping("/api/user/getuserid")
	public ResponseEntity<Long> getUserId(HttpServletRequest request) {
		String token = jwtservice.getToken(request);
		String eamil = jwtservice.extractEmail(token);
		Users user = passservice .getUser(eamil);
		long id = user.getId();
		return ResponseEntity.ok(id);
	}
}
