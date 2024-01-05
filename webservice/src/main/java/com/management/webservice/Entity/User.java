package com.management.webservice.Entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;


	@Column(name = "username")
	private String username;
	

	@Column(name = "email")
	private String email;
	
	
	@Column(name = "password")
	private String password;
	
	
	@Column(name = "active")
	private boolean active = false;
	
	
	@Column(name = "activationtoken")
	private String activationtoken;
	
	@Column(name = "image")
	private String image;
	
	public User() {
		
	}

	
	
	
	public User(long id, String username, String email, String password, boolean active, String activationtoken,
			String image) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.activationtoken = activationtoken;
		this.image = image;
	}




	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getActivationtoken() {
		return activationtoken;
	}

	public void setActivationtoken(String activationtoken) {
		this.activationtoken = activationtoken;
	}




	public String getImage() {
		return image;
	}




	public void setImage(String image) {
		this.image = image;
	}


	
	
	
	
}
