package com.management.webservice.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.management.webservice.Service.FileService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

	String[] types;
	
	private FileService fileService;
	
	public FileTypeValidator(FileService fileService) {
		super();
		this.fileService = fileService;
	}
	
	
	
	
	@Override
	public void initialize(FileType constraintAnnotation) {
		this.types =  constraintAnnotation.types();
	}
	



	//İmage türü jpeg yada png olmalı.
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value== null || value.isEmpty()) return true;
		String type = fileService.detectType(value);
		for(String validType : types) {
			if(type.contains(validType)) return true;
		}
		//mesajları override etme
		String validTypes  = Arrays.stream(types).collect(Collectors.joining(", "));
		context.disableDefaultConstraintViolation();
		HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap
				(HibernateConstraintValidatorContext.class);
		hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
		hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.
				getDefaultConstraintMessageTemplate()).addConstraintViolation();
		
		
		return false;
		
	}

}
