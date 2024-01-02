package com.management.webservice.Service;

import java.util.Properties;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	        sendActivationEmail(savedUser);
	        savedUser.setActivationtoken(UUID.randomUUID().toString());
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			return savedUser;
			
		}catch(DataIntegrityViolationException ex){
			throw new NotUniqueEmailException();
		}
		
	}


	//Email aktive etme	
	private void sendActivationEmail(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@my-app.com");
		message.setTo(user.getEmail());
		message.setSubject("Account Activation");
		message.setText("http://localhost:5173/activation/" + user.getActivationtoken());
		getJavaMailSender().send(message);
	}
	
	
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.ethereal.email");
		mailSender.setPort(587);
		mailSender.setUsername("guy51@ethereal.email");
		mailSender.setPassword("Kd9HvvG8U9UHWD5mmW");
		
		Properties properties = mailSender.getJavaMailProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		return mailSender;
		
		
	}

}



