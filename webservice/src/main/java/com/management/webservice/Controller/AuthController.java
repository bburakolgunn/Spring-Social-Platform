package com.management.webservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Service.AuthService;
import com.management.webservice.dto.AuthResponse;
import com.management.webservice.dto.Credentials;
import com.management.webservice.exception.AuthenticationException;
import com.management.webservice.exception.Error;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}


	@PostMapping("/api/v1/auth")
		AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds) {
		return authService.authenticate(creds);
	}
	
	
	

}
