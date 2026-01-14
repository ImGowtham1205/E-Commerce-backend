package com.example.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_password_token")
public class AdminPasswordResetToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String token;
	private LocalDateTime expirydate;
	@ManyToOne
	private Admins admin;
	
	public boolean isExipry() {
		return expirydate.isBefore(LocalDateTime.now());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(LocalDateTime expirydate) {
		this.expirydate = expirydate;
	}

	public Admins getAdmin() {
		return admin;
	}

	public void setAdmin(Admins admin) {
		this.admin = admin;
	}
	
}
