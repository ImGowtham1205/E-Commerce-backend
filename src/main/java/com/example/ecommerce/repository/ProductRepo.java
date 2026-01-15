package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long>{
	
}
