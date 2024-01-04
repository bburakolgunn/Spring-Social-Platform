package com.management.webservice.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.management.webservice.shared.Messages;

@SuppressWarnings("serial")
public class ActivationNotificationException extends RuntimeException {

	public ActivationNotificationException() {
		super(Messages.getMessageForLocale("webservice.create.user.email.failure", LocaleContextHolder.getLocale()));
	}
	
}
