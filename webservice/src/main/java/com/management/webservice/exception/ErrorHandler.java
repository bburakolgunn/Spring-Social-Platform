package com.management.webservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.management.webservice.shared.Messages;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler({ //Hepsini handle edilecek diyoruz.
		MethodArgumentNotValidException.class,
		NotUniqueEmailException.class,
		ActivationNotificationException.class,
		InvalidTokenException.class,
		NotFoundException.class,
		AuthenticationException.class
		
		
	})
	
	//Hata yönetimi
	ResponseEntity<Error> handleException(Exception exception, HttpServletRequest request ){
		Error error = new Error();
		error.setPath(request.getRequestURI());
		error.setMessage(exception.getMessage());
		if(exception instanceof MethodArgumentNotValidException) {
			String message = Messages.getMessageForLocale("webservice.error.validation",
					LocaleContextHolder.getLocale());
			error.setMessage(message);
			error.setStatus(400);
				Map<String,String> validationError  = new HashMap<>();
		
		//Hataları bakıp hangi field için olduğunu ve hatanın ne için olduğunu söyleyebiliriz.
		//Her bir field error için username,email karşılık gelen hata mesajı must not blank gibi
		//fieldError.getField()email,username, fieldError.getDefaultMessage() must not the blank
		for(var fieldError :((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()){
			validationError.put(fieldError.getField(), fieldError.getDefaultMessage());
			error.setValidationError(validationError);
		}
		
		} else if(exception instanceof NotUniqueEmailException) {
			error.setStatus(400);
			error.setValidationError(((NotUniqueEmailException)exception).getValidationError());
		} else if(exception instanceof ActivationNotificationException) {
			error.setStatus(502);
		}else if(exception instanceof InvalidTokenException) {
			error.setStatus(400);
		}else if (exception instanceof NotFoundException) {
			error.setStatus(403);
		}else if (exception instanceof AuthenticationException) {
			error.setStatus(401);
		}
		
		return ResponseEntity.status(error.getStatus()).body(error);
		
	}
	
	
	
	

}
