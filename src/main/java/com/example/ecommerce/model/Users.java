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
@Table(name ="users",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}),
 		indexes = {@Index(name = "idx_users_email", columnList = "email")})

public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="uname")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phoneno")
	private String phoneno;
	
	@Column(name="pass")
	private String password;
	
	@Column(name="role")
	private String role = "USER";
	
	@Column(name="address",length = 700)
	private String address;
	
	@Column(name = "is_profile_completed")
	private boolean profileCompleted;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
	private List<PasswordResetToken> token;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
	private List<OAuthProviders> oauthProviders;
	
	public long getId() {
		return id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isProfileCompleted() {
		return profileCompleted;
	}
	public void setProfileCompleted(boolean profileCompleted) {
		this.profileCompleted = profileCompleted;
	}	
	
}