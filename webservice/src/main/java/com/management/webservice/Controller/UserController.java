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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.BasicAuthTokenService;
import com.management.webservice.Service.UserService;
import com.management.webservice.Service.impl.TokenServiceImpl;
import com.management.webservice.configuration.CurrentUser;
import com.management.webservice.dto.PasswordResetRequest;
import com.management.webservice.dto.PasswordUpdate;
import com.management.webservice.dto.UserCreate;
import com.management.webservice.dto.UserDTO;
import com.management.webservice.dto.UserUpdate;
import com.management.webservice.exception.ActivationNotificationException;
import com.management.webservice.exception.AuthenticationException;
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
	private BasicAuthTokenService tokenService;
	private TokenServiceImpl tokenServiceImpl;
	
	//private MessageSource messageSource;
	
	public UserController(UserService userService, BasicAuthTokenService tokenService,
			TokenServiceImpl tokenServiceImpl) {
		super();
		this.userService = userService;
		this.tokenService = tokenService;
		this.tokenServiceImpl = tokenServiceImpl;
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
	Page<UserDTO> getUsers(Pageable page,@AuthenticationPrincipal CurrentUser currentUser ){
		return userService.getUsers(page,currentUser).map(UserDTO::new); //Bütüm Page içerisindeki user objeleri UserDTO'ya çevrilmiş olacak.
	}
	
	
	@GetMapping("api/v1/users/{id}")
	UserDTO getUserById(@PathVariable long id) {
		return  new UserDTO(userService.getUser(id));
	}
	
	@PutMapping("api/v1/users/{id}")
	@PreAuthorize("#id == principal.id")//id değeri ile CurrentUser'ın id si arasındaki eşitsizliğe bakmak.Ama biz burada eşit olmasını isteyeceğiz.Çünkü eğer
	//Bu logic eşitse bu metodun execute edilmesi izin vericek
	UserDTO updateUser(@PathVariable long id,@Valid  @RequestBody UserUpdate userUpdate ){ //@AuthenticationPrincipal CurrentUser currentUser   @AuthenticationPrincipal currentuser tipindeki var olan
		//login olan kullanıcıları almak istediğimizi söylüyor. 
		return new UserDTO(userService.updateUser(id, userUpdate));
	}
	
	@DeleteMapping("api/v1/users/{id}")
	@PreAuthorize("#id == principal.id")
	GenericMessage deleteUser(@PathVariable long id ){ 
		userService.deleteUser(id);
		return new GenericMessage("User is deleted");
	}
	
	@PostMapping("api/v1/users/password-reset")
	GenericMessage passwordResetRequest(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
		userService.handleResetRequest(passwordResetRequest);
		return new GenericMessage("Check your email address to reset your password");
	}
	
	@PatchMapping("/api/v1/users/{token}/password")
    GenericMessage setPassword(@PathVariable String token, @Valid @RequestBody PasswordUpdate passwordUpdate){
        userService.updatePassword(token, passwordUpdate);
        return new GenericMessage("Password updated successfully");
    }
		
}



