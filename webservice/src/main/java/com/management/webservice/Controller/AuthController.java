package com.management.webservice.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Service.AuthService;
import com.management.webservice.dto.AuthResponse;
import com.management.webservice.dto.Credentials;
import com.management.webservice.exception.AuthenticationException;
import com.management.webservice.exception.Error;
import com.management.webservice.shared.GenericMessage;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}


	@PostMapping("/api/v1/auth")
		ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
		var authResponse = authService.authenticate(creds);
		var cookie = ResponseCookie.from("kangal-token",authResponse.getToken().getToken()).path("/")
		.httpOnly(true).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
	}
	
	
	@PostMapping("/api/v1/logout")
	ResponseEntity<GenericMessage> handleLogout(@RequestHeader(name = "Authorization", required = false)
	String authorizationHeader, @CookieValue(name ="kangal-token",required = false) String cookieValue) {
		var tokenWithPrefix = authorizationHeader;
		if(cookieValue != null) {
			tokenWithPrefix = "AnyPrefix " + cookieValue;
		}
		authService.logout(tokenWithPrefix);
		var cookie = ResponseCookie.from("kangal-token","").path("/").maxAge(0).httpOnly(true).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body
				(new GenericMessage("Logout success"));
		
	}

	
}
