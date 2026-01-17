package com.example.ecommerce.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Cart;

@Repository
public interface CartRepo extends MongoRepository<Cart, ObjectId>{
	List<Cart> findByUserId(long userid);
}
