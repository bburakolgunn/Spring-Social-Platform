package com.management.webservice.dto;

import com.management.webservice.Entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserDTO {
	

	
	private long id;


	
	private String username;
	

	
	private String email;
	

	
	
	private String image;
	
	
	
	public UserDTO() {
		
	}



	public UserDTO(User user) {
		setId(user.getId());
		setUsername(user.getUsername());
		setEmail(user.getEmail());
		setImage(user.getImage());
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getImage() {
		return this.image;
	}



	public void setImage(String image) {
		this.image = image;
	}


	
	


}
