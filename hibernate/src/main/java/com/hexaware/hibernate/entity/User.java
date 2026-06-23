package com.hexaware.hibernate.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")
	private int userId;
	@Column(name="roleId")
	private int roleId;
	@Column(name="userName")
	private String username;
	@Column(name="email")
	private String email;
	@Column(name="password_hash")
	private String passwordHash;
	@Column(name="fullName")
	private String fullName;
	@Column(name="gender")
	private String gender; // MALE | FEMALE | OTHER
	@Column(name="contactNumber")
	private String contactNumber;
	@Column(name="address")
	private String address;
	@Column(name="isActive")
	private boolean isActive;
	@Column(name="emailVerified")
	private boolean emailVerified;
	@Column(name="profileImage")
	private String profileImage;
	@Column
	private LocalDateTime lastLoginAt;
	@Column
	private LocalDateTime createdAt;
	@Column
	private LocalDateTime updatedAt;
	
	@OneToOne(mappedBy = "email", cascade = CascadeType.ALL)
	private Passenger passenger;
	

	// ── Constructors ──────────────────────────────────────────
	public User() {
	}

	
	

	public User(int roleId, String username, String email, String passwordHash, String fullName, Passenger passenger) {
		super();
		this.roleId = roleId;
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.fullName = fullName;
		this.passenger = passenger;
	}




	public Passenger getPassenger() {
		return passenger;
	}


	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}


	// ── Getters and Setters ───────────────────────────────────
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "User{userId=" + userId + ", username='" + username + "', email='" + email + "', roleId=" + roleId + "}";
	}
}
