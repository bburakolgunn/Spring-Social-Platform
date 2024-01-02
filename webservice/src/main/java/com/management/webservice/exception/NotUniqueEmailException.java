package com.management.webservice.exception;


import java.util.Collections;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import com.management.webservice.shared.Messages;

public class NotUniqueEmailException extends RuntimeException {
	
	 public NotUniqueEmailException() {
	        super(Messages.getMessageForLocale("webservice.error.validation",LocaleContextHolder.getLocale()));
	    }
	 
	 public Map<String,String> getValidationError(){
		 return Collections.singletonMap("email", Messages.getMessageForLocale("webservice.constraint.email.notunique",
				 LocaleContextHolder.getLocale()));
	 }

}



