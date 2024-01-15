package com.management.webservice.Service;

import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.Service.impl.AuthServiceImpl;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.dto.AuthResponse;
import com.management.webservice.dto.Credentials;
import com.management.webservice.dto.UserDTO;
import com.management.webservice.exception.AuthenticationException;

@Service
public class AuthService implements AuthServiceImpl {
	
	private UserService userService;				//Database'e erişim için isteseydik UserService UserRepository'i kullanabilirdik
	private TokenServiceImpl tokenServiceImpl;												//AMA!!!
													//Çünkü servisler arka planda modelin nasıl access edileceğini ya da modelin
													//nasıl güncelleme ile ilgili logic barındırır.
													//userRepository üzerinden doğrudan users tablosuna müdahale etmek yerine
													//service aracılığıyla bu işlemi yaparak daha kontrollü bir model database de tutulur.
	
	
	
	private PasswordEncoder passwordEncoder;



	public AuthService(UserService userService,TokenServiceImpl tokenServiceImpl,PasswordEncoder passwordEncoder) {
		super();
		this.userService = userService;
		this.tokenServiceImpl = tokenServiceImpl;
		this.passwordEncoder = passwordEncoder;
		}




	@Override
	//Email ve Password var Credentials'da,burada database'den user'ı bulunur ve password ile eşleşiyor mu?
	public AuthResponse authenticate(Credentials creds) {
		User inDB = userService.findByEmail(creds.email());
		if(inDB == null) throw new AuthenticationException();
		if(!passwordEncoder.matches(creds.password(), inDB.getPassword())) throw new AuthenticationException();
		
		Token token = tokenServiceImpl.createToken(inDB, creds);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken(token);
		authResponse.setUser(new UserDTO(inDB));
		return authResponse;
		
	}

	
	
	
	
}
