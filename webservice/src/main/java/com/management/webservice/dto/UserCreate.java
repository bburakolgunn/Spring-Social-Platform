package com.management.webservice.dto;

import com.management.webservice.Entity.User;
import com.management.webservice.validation.UniqueEmail;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
		
		
	@NotBlank(message  = "{webservice.constraint.username.notblank}")
	//@NotNull(message  ="Username not null")
	@Column(name = "username")
	@Size(min = 4, max =100)
	 String username,
	
	@NotBlank
	//@NotNull(message  ="Email not null")
	@Email 
	@Column(name = "email")
	@UniqueEmail (message = "{webservice.constraint.email.notunique}")
	 String email,
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{webservice.constraint.password.pattern}")
	@Size(min = 4, max =100)
	//@NotBlank(message  ="Password can't be blank")
	//@NotNull(message  ="Password not null")
	@Column(name = "password")
	 String password
	) {
	
	public User toUser() {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		return user;
		
		
	}
	
	

}




