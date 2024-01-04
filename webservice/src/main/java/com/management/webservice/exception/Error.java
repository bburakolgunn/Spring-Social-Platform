package com.management.webservice.exception;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(value = Include.NON_NULL)
public class Error {
	
	private int status;
	
	private String message;
	
	private String path;
	
	private long timestamp = new Date().getTime();
	
	private Map<String, String> validationError = null; //Burada çünkü neden null yapıldı
	//Çünkü ActivationNotification exception da validatiton array olarak gözükmektedir.
	//Burada JsonInclude yaparak null olmayan field ları json'a ekle diyoruz.
	
	public Error() {
		
	}
	

	public Error(int status, String message, String path, long timestamp, Map<String, String> validationError) {
		super();
		this.status = status;
		this.message = message;
		this.path = path;
		this.timestamp = timestamp;
		this.validationError = validationError;
	}








	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, String> getValidationError() {
		return validationError;
	}

	public void setValidationError(Map<String, String> validationError) {
		this.validationError = validationError;
	}
	
	

}





