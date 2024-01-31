package com.management.webservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Token {

	@Transient //database de kolonu olmayacak.
	private String prefix = "Bearer";
	
	@Id
	@Column(name = "token")
	private String token;
	
	@JsonIgnore
	@ManyToOne //Bir kullanıcının birden fazla token'ı olabilir.
	User user;



	public Token(String prefix, String token) { //JwtTokenService de kullanıldığı için parametreli
		super();
		this.prefix = prefix;
		this.token = token;
	} 
	
	public Token() { //Jpa için default constructor
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
}
