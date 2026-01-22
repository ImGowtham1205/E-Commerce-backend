package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long>{
	Users findByEmail(String email);
	Users findById(long id);
	boolean existsByEmail(String email);
	boolean existsByPhoneno(String phoneno);
}
