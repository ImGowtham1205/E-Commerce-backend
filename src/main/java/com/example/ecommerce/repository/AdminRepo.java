package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Admins;

@Repository
public interface AdminRepo extends JpaRepository<Admins, Long>{
	Admins findByEmail(String email);
}
