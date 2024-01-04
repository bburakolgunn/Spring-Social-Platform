package com.management.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

}
