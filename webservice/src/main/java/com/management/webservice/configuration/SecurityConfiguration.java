package com.management.webservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	private  TokenFilter tokenFilter;
	
	public SecurityConfiguration(TokenFilter tokenFilter) {
		super();
		this.tokenFilter = tokenFilter;
	}


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authentication) -> 
		authentication.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"api/v1/users/{id}")).authenticated()
		//endpoint gelen put isteklerini authenticad et,geri kalanlar permit ol.
		.anyRequest().permitAll()
		);
		http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new AuthEntryPoint()));
		
		
		http.csrf(csrf -> csrf.disable()); //Postmanda POST edildiği zaman 403 hatası dönüyor JSON boş iken,401 hatası dönmesi gerektiği için
		http.headers(headers -> headers.disable());
		
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	
	
	
	
	

}
