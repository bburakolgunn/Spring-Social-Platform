package com.management.webservice.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.management.webservice.shared.Messages;

@SuppressWarnings("serial")
public class InvalidTokenException extends RuntimeException {
	
	public InvalidTokenException() {
		super(Messages.getMessageForLocale("webservice.activate.user.invalid.token", LocaleContextHolder.getLocale()));
	}

}
