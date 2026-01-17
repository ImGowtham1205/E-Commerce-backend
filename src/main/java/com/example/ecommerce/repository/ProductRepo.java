package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Products;
import com.example.ecommerce.projection.ProductView;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long>{
	List<ProductView> findByCategory(String category);
}
