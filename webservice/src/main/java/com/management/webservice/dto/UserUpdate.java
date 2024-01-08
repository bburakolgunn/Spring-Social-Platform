package com.management.webservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate(
		
		@NotBlank(message  = "{webservice.constraint.username.notblank}")
		//@NotNull(message  ="Username not null")
		@Column(name = "username")
		@Size(min = 4, max =100)
		 String username
		
		
		) {
	
	

}
