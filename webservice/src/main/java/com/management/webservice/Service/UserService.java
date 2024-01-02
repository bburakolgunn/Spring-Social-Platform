package com.management.webservice.Service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.impl.UserServiceImpl;
import com.management.webservice.exception.NotUniqueEmailException;


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
		
		try {
			
			User savedUser = userRepository.save(user);
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			return savedUser;
			
		}catch(DataIntegrityViolationException ex){
			throw new NotUniqueEmailException();
		}
		
	}

}



