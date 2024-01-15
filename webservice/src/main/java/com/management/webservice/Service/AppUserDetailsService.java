package com.management.webservice.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.User;
import com.management.webservice.configuration.CurrentUser;

//Spring Security userları nerede aramasını gerektiğini bilmeli için sınıf

@Service
public class AppUserDetailsService implements UserDetailsService {//Userları bulmak için aradığını bir interface

	private UserService userService;
	
	public AppUserDetailsService(UserService userService) {
	super();
	this.userService = userService;
}



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User inDB = userService.findByEmail(email);
		if(inDB == null) {
			throw new UsernameNotFoundException(email + "is not found");
		}
		
		
		
		return new CurrentUser(inDB);
			
			
		}
	
	} 

