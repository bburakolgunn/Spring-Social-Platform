package com.management.webservice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.UserService;
import com.management.webservice.dto.UserCreate;
import com.management.webservice.dto.UserDTO;
import com.management.webservice.exception.ActivationNotificationException;
import com.management.webservice.exception.Error;
import com.management.webservice.exception.InvalidTokenException;
import com.management.webservice.exception.NotFoundException;
import com.management.webservice.exception.NotUniqueEmailException;
import com.management.webservice.shared.GenericMessage;
import com.management.webservice.shared.Messages;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private UserService userService;
	//private MessageSource messageSource;
	
	
	public UserController(UserService userService ) {
		
		this.userService = userService;
	
	}


	@PostMapping("/api/v1/users")
	public ResponseEntity<GenericMessage> createUser(@Valid @RequestBody UserCreate user) {
	    userService.save(user.toUser());
	    String message = Messages.getMessageForLocale("webservice.create.user.success.message",
	            LocaleContextHolder.getLocale());
	    GenericMessage genericMessage = new GenericMessage(message);
	    return new ResponseEntity<>(genericMessage, HttpStatus.CREATED);
	}
	
	
	@PatchMapping("api/v1/users/{token}/active")
	public ResponseEntity<GenericMessage> activateUer(@PathVariable String token) {
	    userService.activateUser(token);
	    String message = Messages.getMessageForLocale("webservice.activate.user.success.message",
	            LocaleContextHolder.getLocale());
	    GenericMessage genericMessage = new GenericMessage(message);
	    return new ResponseEntity<>(genericMessage, HttpStatus.CREATED);
    }
	
	@GetMapping("api/v1/users")
	Page<UserDTO> getUsers(Pageable page){
		return userService.getUsers(page).map(UserDTO::new); //Bütüm Page içerisindeki user objeleri UserDTO'ya çevrilmiş olacak.
	}
	
	@GetMapping("api/v1/users/{id}")
	UserDTO getUserById(@PathVariable long id) {
		return  new UserDTO(userService.getUser(id));
	}
	
	
	

	
	
		
		
}



