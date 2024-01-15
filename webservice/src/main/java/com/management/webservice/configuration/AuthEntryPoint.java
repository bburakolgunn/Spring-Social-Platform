package com.management.webservice.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//exceptionları resolve eden bir fonksiyona delege edilecek.

public class AuthEntryPoint implements AuthenticationEntryPoint {
	
	@Qualifier("handlerExceptionResolver") //debugdan bu hata ortaya çıkmaktadır.injekt ediyoruz.
	private HandlerExceptionResolver exceptionResolver;
	
	
	public AuthEntryPoint(HandlerExceptionResolver exceptionResolver) {
		super();
		this.exceptionResolver = exceptionResolver;
	}


	public AuthEntryPoint() {
	}


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	exceptionResolver.resolveException(request, response, null, authException);
		
	}

}
