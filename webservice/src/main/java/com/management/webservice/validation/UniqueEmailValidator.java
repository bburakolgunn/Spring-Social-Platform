package com.management.webservice.validation;

import org.springframework.stereotype.Component;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private UserRepository userRepository;
	
	public UniqueEmailValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}




	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		  User inDB = userRepository.findByEmail(value);
		 return inDB == null;
	}

}
