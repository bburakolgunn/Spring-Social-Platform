package com.management.webservice.Service;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.webservice.Entity.Token;
import com.management.webservice.Entity.User;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.dto.Credentials;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtTokenService implements TokenServiceImpl {
	
	SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());
	
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Token createToken(User user, Credentials creds) {
		TokenSubject tokenSubject = new TokenSubject(user.getId(), user.isActive());
		try {
			String subject = mapper.writeValueAsString(tokenSubject);
			String token = Jwts.builder().setSubject(subject).signWith(key).compact();
			return new Token("Bearer", token);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		} //objeyi json string'e çevirme
		return null;
	}

	@Override
	public User verifyToken(String authorizationHeader) {
		if(authorizationHeader == null) return null;
		var token = authorizationHeader.split(" ")[1];
		JwtParser parser = Jwts.parser().setSigningKey(key).build();
		try {
		Jws<Claims> claims = parser.parseClaimsJws(token); //Yetkilerle ilgili datalara erişme kısmı
		var subject = claims.getBody().getSubject();
		var tokenSubject = mapper.readValue(subject, TokenSubject.class);
		User user = new User();
		user.setId(tokenSubject.id());
		user.setActive(tokenSubject.active());
		return user;
		}catch(JwtException | JsonProcessingException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static record TokenSubject(long id,boolean active) {
		
	}

	@Override
	public void logout(String authorizationHeader) {
		// TODO Auto-generated method stub
		
	}

}




