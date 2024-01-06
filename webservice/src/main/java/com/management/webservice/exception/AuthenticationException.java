package com.management.webservice.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.management.webservice.shared.Messages;

public class AuthenticationException extends RuntimeException {
	
	public AuthenticationException() {
		super(Messages.getMessageForLocale("webservice.auth.invalid.credentials",LocaleContextHolder.getLocale()));
	}
	
}
