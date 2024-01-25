package com.management.webservice.Service;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;
import com.management.webservice.Service.impl.UserServiceImpl;
import com.management.webservice.configuration.CurrentUser;
import com.management.webservice.dto.UserDTO;
import com.management.webservice.dto.UserUpdate;
import com.management.webservice.exception.ActivationNotificationException;
import com.management.webservice.exception.InvalidTokenException;
import com.management.webservice.exception.NotFoundException;
import com.management.webservice.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;



@Service
public class UserService implements UserServiceImpl {
	
	private  UserRepository userRepository;
	private  EmailService emailService;
	private PasswordEncoder passwordEncoder;
	private FileService fileService;

	public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder,
			FileService fileService) {
		super();
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.fileService = fileService;
	}


	@Override
	@Transactional(rollbackOn = MailException.class)
	//Spring'e diyoruz eğer bu metot bu fonksiyon bir MailException ile karşılarsa database yazılan verileri geri al.
	public User save(User user) {
	    try {
	        User savedUser = userRepository.save(user);
	        savedUser.setActivationtoken(UUID.randomUUID().toString());
	        savedUser.setPassword(passwordEncoder.encode(savedUser.getActivationtoken()));
	        userRepository.save(savedUser);
	        emailService.sendActivationEmail(savedUser.getEmail(),savedUser.getActivationtoken());
	        return savedUser;

	    } catch (DataIntegrityViolationException ex) {
	        throw new NotUniqueEmailException();
	    }catch(MailException ex) {
	    	throw new ActivationNotificationException();
	    }
	}


	@Override
	public void activateUser(String token) {
		User inDB = userRepository.findByactivationtoken(token);
		if(inDB == null) {
			throw new InvalidTokenException();
		}
		inDB.setActive(true);
		inDB.setActivationtoken(null);
		userRepository.save(inDB);
	}



	@Override
	public Page<User> getUsers(Pageable page,CurrentUser currentUser) {
		if(currentUser == null) {
			return userRepository.findAll(page);
		}
		return userRepository.findByIdNot(currentUser.getId(), page);
	}



	@Override
	public User getUser(long id) { //
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id)); 
		
		//getReferenceById(id);Geriye user objesi döner,yoksa da exception atar
	}


	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email) ;
	}



	@Override
	public User updateUser(long id, UserUpdate userUpdate) {
		User inDB = getUser(id); //Yoksa exception dönecek.
		inDB.setUsername(userUpdate.username());
		if(userUpdate.image() != null) {
			String fileName = fileService.saveBase64StringAsFile(userUpdate.image());
			fileService.deleteProfileImage(inDB.getImage()); // eski profil image silme
			inDB.setImage(fileName);//user objesinde o dosya adında bir referans tutulacak.
		}
		
		return userRepository.save(inDB);
	}






	
	
	


	

}



