package com.management.webservice.Service.impl;

import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.dto.Credentials;

@Service
public interface TokenServiceImpl  {
	
	public Token createToken(User user, Credentials creds); //Token'i oluşturma
	
	public User verifyToken(String authorizationHeader);
	
	public void logout(String authorizationHeader);

}
