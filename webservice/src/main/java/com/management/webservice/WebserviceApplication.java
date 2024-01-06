package com.management.webservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.management.webservice.Entity.User;
import com.management.webservice.Repository.UserRepository;









@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}
	
	//Developement süreci için yazılmıştır,production da gözükmemektedir.Burada test edilmiştir.
	//25 adet user kullanılmıştır.
	@Bean
	@Profile("dev")
	CommandLineRunner userCreator(UserRepository userRepository) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return (args) -> {
			for(var i=1;i<=10;i++) {
				User user = new User();
				user.setUsername("user" + i);
				user.setEmail("user"+i+"@mail.com");
				user.setPassword(passwordEncoder.encode("P4ssword"));
				user.setActive(true);
				userRepository.save(user);
			}
		};
	}

}
