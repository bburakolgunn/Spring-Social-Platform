package com.management.webservice.dto;

import com.management.webservice.Entity.Token;

public class AuthResponse {
	
	UserDTO user;
	
	Token token;

	public AuthResponse(UserDTO user, Token token) {
		super();
		this.user = user;
		this.token = token;
	}
	
	public AuthResponse() {
		
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	

}
