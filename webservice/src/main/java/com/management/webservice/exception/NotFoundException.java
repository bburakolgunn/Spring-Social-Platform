package com.management.webservice.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.management.webservice.shared.Messages;

public class NotFoundException extends RuntimeException {
	
	//Bu exception'ı atarken bulunamayan kullancının id'sini parametre olarak verilir ve bu id'yi daha sonra
	//mesajın içerisinde kullanılır.
	public NotFoundException(long id) {
		super(Messages.getMessageForLocale("webservice.user.not.found",LocaleContextHolder.getLocale(), id));
		
	}

}
