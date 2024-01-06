package com.management.webservice.Service;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.dto.Credentials;

@Service
public class BasicAuthTokenService implements TokenServiceImpl {

	@Override
	public Token createToken(User user, Credentials creds) { //Base64 Aunthencoded
		String emailColonPassword = creds.email() + ":" + creds.password();
		String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
		return new Token("Basic", token);
	}

	@Override
	public User verifyToken(String authorizationHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}
