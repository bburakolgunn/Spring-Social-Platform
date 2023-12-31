package com.management.webservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "webservice") //webservice ile başlayan konfigürasyonlar bu class'ın içinde tutulacak.
//Spring application.properties deki webservice ile başlayanları bu class'ın içinde denk gelen field'lara set edilecek.
@Configuration //Böylece Spring yukarıdaki prefix'teki properties'leri burada oluşturduğu class'ın değerlerine assignment
//etmiş olacak.
//Bu şekilde EmailService yazılan revizyon ile değerleri artık application.properties den alındı.Daha yönetilebilir ve 
//daha esnek bir tasarım oldu.
public class WebServiceProperties {

	private Email email;
	
	private Client client;
	
	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public static record Email(
			
		String username,
		String password,
		String host,
		int port,
		String from
			
			) {}
	
	public static record Client(
			
		String host
			
			) {}
	
	
}
