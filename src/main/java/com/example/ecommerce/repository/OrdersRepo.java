package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long>{
	List<Orders> findByUserid(long userid);
}
