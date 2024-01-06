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
	
	//Hata yönetimi
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Error> handleMethodArgNotValidEx(MethodArgumentNotValidException exception, HttpServletRequest request ){
		Error error = new Error();
		error.setPath(request.getRequestURI());
		String message = Messages.getMessageForLocale("webservice.error.validation",
				LocaleContextHolder.getLocale());
		error.setMessage(message);
		error.setStatus(400);
		Map<String,String> validationError  = new HashMap<>();
		
		//Hataları bakıp hangi field için olduğunu ve hatanın ne için olduğunu söyleyebiliriz.
		//Her bir field error için username,email karşılık gelen hata mesajı must not blank gibi
		//fieldError.getField()email,username, fieldError.getDefaultMessage() must not the blank
		for(var fieldError : exception.getBindingResult().getFieldErrors()){
			validationError.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		error.setValidationError(validationError);
		return ResponseEntity.badRequest().body(error);
	}
	
	
	//Hata yönetimi
			@ExceptionHandler(NotUniqueEmailException.class)
			@ResponseStatus(HttpStatus.OK.BAD_REQUEST)
			ResponseEntity<Error> handleNotUniqueEmailEx(NotUniqueEmailException exception){
				Error error = new Error();
				error.setPath("/api/v1/users");
				error.setMessage(exception.getMessage());
				error.setStatus(400);
				error.setValidationError(exception.getValidationError());
				return ResponseEntity.status(400).body(error);
			}
			
			
			@ExceptionHandler(ActivationNotificationException.class)
			ResponseEntity<Error> handleActivationNotificationException(ActivationNotificationException exception){
				Error error = new Error();
				error.setPath("/api/v1/users");
				error.setMessage(exception.getMessage());
				error.setStatus(502);
				return ResponseEntity.status(502).body(error);
			}
			
			
			@ExceptionHandler(InvalidTokenException.class)
			ResponseEntity<Error> handleInvalidTokenException(InvalidTokenException exception,HttpServletRequest request){
				Error error = new Error();
				error.setPath(request.getRequestURI());
				error.setMessage(exception.getMessage());
				error.setStatus(400);
				return ResponseEntity.status(400).body(error);
			}
			
			@ExceptionHandler(NotFoundException.class)
			ResponseEntity<Error> handleNotFoundException(NotFoundException exception, HttpServletRequest request){
				Error error = new Error();
				error.setPath(request.getRequestURI());
				error.setMessage(exception.getMessage());
				error.setStatus(404);
				return ResponseEntity.status(404).body(error);
			}
			
	
			@ExceptionHandler(AuthenticationException.class)
			ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) {
				Error error = new Error();
				error.setPath("/api/v1/auth");
				error.setStatus(401); // An Authorized
				error.setMessage(exception.getMessage());
				return ResponseEntity.status(401).body(error);
			}
	
	

}
