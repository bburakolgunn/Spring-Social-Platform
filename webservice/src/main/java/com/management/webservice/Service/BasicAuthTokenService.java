package com.management.webservice.Service;

import java.util.Base64;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.dto.Credentials;

@Service
//@ConditionalOnProperty(name = "webservice.token-type", havingValue = "basic")
public class BasicAuthTokenService implements TokenServiceImpl {
	
	
	 private UserService userService; //Database'den User'ı Query edilecek
	 private PasswordEncoder passwordEncoder;

	public BasicAuthTokenService(UserService userService, PasswordEncoder passwordEncoder) {
		super();
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Token createToken(User user, Credentials creds) { //Base64 Aunthencoded
		String emailColonPassword = creds.email() + ":" + creds.password();
		String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
		return new Token("Basic", token);
	}

	@Override
	public User verifyToken(String authorizationHeader) {
		if(authorizationHeader == null) return null;
		var base64Encoded = authorizationHeader.split("Basic ")[1];
		 var decoded = new String (Base64.getDecoder().decode(base64Encoded));
		 var credentials = decoded.split(":");
		 var email = credentials[0];
		 var password = credentials[1];
		 User inDB = userService.findByEmail(email);
		 if(inDB == null) return null;
		 if(!passwordEncoder.matches(password, inDB.getPassword())) return null;
		 return inDB;
		 
	}

}



