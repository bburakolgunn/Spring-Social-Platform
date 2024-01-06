package com.management.webservice.Service.impl;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.dto.Credentials;

public interface TokenServiceImpl  {
	
	public Token createToken(User user, Credentials creds); //Token'i oluşturma
	
	public User verifyToken(String authorizationHeader);
	
	

}
