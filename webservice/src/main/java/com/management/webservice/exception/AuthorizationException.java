package com.management.webservice.exception;

public class AuthorizationException extends RuntimeException {
	
	public AuthorizationException() {
		super("Forbidden");
	}

}
