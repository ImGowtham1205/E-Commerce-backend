package com.example.ecommerce.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "admins",uniqueConstraints = @UniqueConstraint(columnNames = {"email","phoneno"}),
		indexes = {@Index(name = "idx_admins_email", columnList = "email")})

public class Admins {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "aname",nullable = false)
	private String adminName;
	
	@Column(nullable = false)
	private String phoneno;
	
	@Column(nullable = false)
	private String email;
	
	@Column(name = "pass",nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role = "ADMIN";
	
	@OneToMany(mappedBy = "admin",cascade = CascadeType.REMOVE,orphanRemoval = true)
	private List<AdminPasswordResetToken> token;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Admins [id=" + id + ", adminName=" + adminName + ", phoneno=" + phoneno + ", email=" + email
				+ ", password=" + password + "]";
	}
}
