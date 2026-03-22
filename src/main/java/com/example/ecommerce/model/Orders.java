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
	private LocalDate orderdate;
	private LocalTime ordertime;
	private String payment_Status;
	private String order_status = "NOT DELIVERED";
	private String username;
	private String address;
	private String phoneno;
	private long userid;
	private String paymentmethod;
	private String paymentid;
	private String refundid;
	private String refundstatus;
	
	public long getOrderid() {
		return orderid;
	}
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}
	public String getRefundid() {
		return refundid;
	}
	public void setRefundid(String refundid) {
		this.refundid = refundid;
	}
	public String getRefundstatus() {
		return refundstatus;
	}
	public void setRefundstatus(String refundstatus) {
		this.refundstatus = refundstatus;
	}
}