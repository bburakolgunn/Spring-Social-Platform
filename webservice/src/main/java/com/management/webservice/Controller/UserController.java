package com.management.webservice.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.UserService;
import com.management.webservice.dto.UserCreate;
import com.management.webservice.exception.Error;
import com.management.webservice.exception.NotUniqueEmailException;
import com.management.webservice.shared.GenericMessage;
import com.management.webservice.shared.Messages;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private UserService userService;
	//private MessageSource messageSource;
	
	
	public UserController(UserService userService, MessageSource messageSource) {
		super();
		this.userService = userService;
		//this.messageSource = messageSource;
	}


	@PostMapping("/api/v1/users")
   public ResponseEntity<?> save(@Valid @RequestBody UserCreate user){
		User savedUser = userService.save(user.toUser());
		String message = Messages.getMessageForLocale("webservice.create.user.success.message"
				,LocaleContextHolder.getLocale());
		GenericMessage genericMessage = new GenericMessage(message);
		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
    }	
	
	
	//Hata yönetimi
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.OK.BAD_REQUEST)
	ResponseEntity<Error> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
		Error error = new Error();
		error.setPath("/api/v1/users");
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
			return ResponseEntity.badRequest().body(error);
		}

}



