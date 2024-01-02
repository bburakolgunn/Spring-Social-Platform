package com.management.webservice.Entity;

import com.management.webservice.validation.UniqueEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	//@Size(min = 4, max =100)
	@NotBlank(message  = "{webservice.constraint.username.notblank}")
	//@NotNull(message  ="Username not null")
	@Column(name = "username")
	private String username;
	
	@NotBlank
	//@NotNull(message  ="Email not null")
	@Email
	@Column(name = "email")
	@UniqueEmail
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{webservice.constraint.password.pattern}")
	@Size(min = 4, max =100)
	//@NotBlank(message  ="Password can't be blank")
	//@NotNull(message  ="Password not null")
	@Column(name = "password")
	private String password;
	
	public User() {
		
	}
	

	
	
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
