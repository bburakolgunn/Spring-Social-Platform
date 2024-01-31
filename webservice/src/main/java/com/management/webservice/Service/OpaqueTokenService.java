package com.management.webservice.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.Repository.TokenRepository;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.dto.Credentials;

@Service
@Primary
public class OpaqueTokenService implements TokenServiceImpl {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	

	@Override
	public Token createToken(User user, Credentials creds) {
		String randomValue = UUID.randomUUID().toString();
		Token token = new Token();
		token.setToken(randomValue);
		token.setUser(user);
		return tokenRepository.save(token);
	}

	@Override
	public User verifyToken(String authorizationHeader) {
		var tokenInDB = getToken(authorizationHeader);
		if(!tokenInDB.isPresent()) return null;
		return tokenInDB.get().getUser();
	}

	@Override
	public void logout(String authorizationHeader) {
		var tokenInDB = getToken(authorizationHeader);
		if(!tokenInDB.isPresent()) return;
		tokenRepository.delete(tokenInDB.get());
	}
	
	private Optional<Token> getToken(String authorizationHeader){
		if(authorizationHeader == null) return Optional.empty();
		var token = authorizationHeader.split(" ")[1];
		return tokenRepository.findById(token);
	}
	
}
