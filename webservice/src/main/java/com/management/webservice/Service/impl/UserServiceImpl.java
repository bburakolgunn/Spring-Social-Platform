package com.management.webservice.Service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.management.webservice.Entity.User;
import com.management.webservice.dto.UserDTO;

public interface UserServiceImpl {
	
	User save(User user);
	
	public void activateUser(String token);
	
	Page<User> getUsers(Pageable page);
	
	User getUser(long id);
	
	User findByEmail(String email);
}
