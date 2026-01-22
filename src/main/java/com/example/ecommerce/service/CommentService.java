package com.example.ecommerce.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Comment;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.CommentRepo;
import com.example.ecommerce.repository.UserRepo;

@Service
public class CommentService {
	
	private CommentRepo commentrepo;
	private UserRepo userrepo;
	
	public CommentService(CommentRepo commentrepo,UserRepo userrepo) {
		this.commentrepo = commentrepo;
		this.userrepo = userrepo;
	}
	
	public ResponseEntity<String> addComment(Comment cmt){
		commentrepo.save(cmt);
		return ResponseEntity.ok("Comment added successfully");
	}
	
	public long getCommentCountForProduct(long productid) {
		return commentrepo.countByProductid(productid);
	}
	
	public ResponseEntity<String> updateComment(Comment cmt){
		commentrepo.save(cmt);
		return ResponseEntity.ok("Comment updated successfully");
	}
	
	public ResponseEntity<String> deleteComment(ObjectId id){
		commentrepo.deleteById(id);
		return ResponseEntity.ok("Comment deleted successfully");
	}
	
	public List<Comment> getCommentForproduct(long productid){
		return commentrepo.findByProductid(productid);
	}
	
	public Users getUserById(long id) {
		return userrepo.findById(id);
	}
}
