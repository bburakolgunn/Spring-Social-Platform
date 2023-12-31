package com.management.webservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	//@NotBlank(message  ="Username can't be blank")
	//@NotNull(message  ="Username not null")
	@Column(name = "username")
	private String username;
	
	//@NotBlank(message  ="Email can't be blank")
	//@NotNull(message  ="Email not null")
	//@Email(message = "Email should be valid")
	@Column(name = "email")
	private String email;
	
	//@NotBlank(message  ="Password can't be blank")
	//@NotNull(message  ="Password not null")
	@Column(name = "password")
	private String password;
	
	

	
	
	public User(long id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
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

	
	
	
	
}
