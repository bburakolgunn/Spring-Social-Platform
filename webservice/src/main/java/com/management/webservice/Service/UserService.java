package com.management.webservice.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.impl.UserServiceImpl;


@Service
public class UserService implements UserServiceImpl {
	
	private final UserRepository userRepository;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public User save(User user) {
		User savedUser = userRepository.save(user);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return savedUser;
		
	}

}



