package com.example.ecommerce.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderid;
	private long productid;
	private long userid;
	private LocalDate orderdate;
	private LocalTime ordertime;
	private String payment_Status ="Success";
	private String order_status = "NOT DELIVERED";
	
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public long getProductid() {
		return productid;
	}
	public void setProductid(long productid) {
		this.productid = productid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public LocalDate getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(LocalDate orderdate) {
		this.orderdate = orderdate;
	}
	public LocalTime getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(LocalTime ordertime) {
		this.ordertime = ordertime;
	}
	public String getPayment_Status() {
		return payment_Status;
	}
	public void setPayment_Status(String payment_Status) {
		this.payment_Status = payment_Status;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
}