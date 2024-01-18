package com.management.webservice.validation;

import com.management.webservice.Service.FileService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

	private FileService fileService;
	
	public FileTypeValidator(FileService fileService) {
		super();
		this.fileService = fileService;
	}



	//İmage türü jpeg yada png olmalı.
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value== null || value.isEmpty()) return true;
		String type = fileService.detectType(value);
		if(type.equals("image/jpeg") || type.equals("image/png")) return true;
		return false;
		
	}

}
