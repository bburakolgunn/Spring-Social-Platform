package com.management.webservice.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.UserService;
import com.management.webservice.exception.Error;
import com.management.webservice.shared.GenericMessage;

@RestController
public class UserController {
	
	private UserService userService;
	

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}




	@PostMapping("/api/v1/users")
   public ResponseEntity<?> save(@Validated @RequestBody User user){
		if(user.getUsername() == null || user.getUsername().isEmpty()) {
			Error error = new Error();
			error.setPath("api/v1/users");
			error.setMessage("Validation error");
			error.setStatus(400);
			Map<String,String> validationErrors = new HashMap<>();
			validationErrors.put("username","Username cannot be null");
			error.setValidationError(validationErrors);
			return ResponseEntity.badRequest().body(error);
		}
		
		
		User savedUser = userService.save(user);
		GenericMessage genericMessage = new GenericMessage("User saved successfully");
		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
    }	

}



