package com.management.webservice.Service.impl;

public interface FileServiceImpl {
	
	String saveBase64StringAsFile(String image);
	
	String detectType(String value);
	
	void deleteProfileImage(String image);

}
