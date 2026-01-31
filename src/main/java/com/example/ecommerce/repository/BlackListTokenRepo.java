package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.BlackListedToken;

public interface BlackListTokenRepo extends JpaRepository<BlackListedToken, Long>{
	boolean existsByToken(String token);
}
