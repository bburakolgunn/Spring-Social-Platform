package com.management.webservice.configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {

	private WebServiceProperties webServiceProperties;
	
	public StaticResourceConfiguration(WebServiceProperties webServiceProperties) {
		super();
		this.webServiceProperties = webServiceProperties;
	}



	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = Paths.get(webServiceProperties.getStorage().getRoot()).toAbsolutePath().toString() + "/";
		registry.addResourceHandler("/assets/**")
				.addResourceLocations("file:" + path);
		
	}
	
	@Bean
	CommandLineRunner createStorageDirs() {
			return args -> {
			createFolder(Paths.get(webServiceProperties.getStorage().getRoot()));
			createFolder(Paths.get(webServiceProperties.getStorage().getRoot(),webServiceProperties.getStorage().getProfile()));
				
			};
	}

	private void createFolder(Path path) {
		File file = path.toFile();
		boolean isFolderExist = file.exists() && file.isDirectory();
		if(!isFolderExist) {
			file.mkdir();
		}
		
	}

	
	
}
