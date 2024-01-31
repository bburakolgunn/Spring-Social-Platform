package com.management.webservice.Service.impl;

import com.management.webservice.dto.AuthResponse;
import com.management.webservice.dto.Credentials;

public interface AuthServiceImpl {
	
	
	
	 AuthResponse authenticate(Credentials creds);
	 
	 public void logout(String authorizationHeader);

}
