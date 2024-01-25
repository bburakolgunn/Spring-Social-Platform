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
	
	private Storage storage = new Storage(); //default değerlerle initialize olabilmesi için instance oluşturulur.
	
	private String tokenType;
	
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

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
	
	public static class Storage{  //default değerden dolayı class dedik.
	
		String root = "uploads";
		String profile = "profile";
		public String getRoot() {
			return root;
		}
		public void setRoot(String root) {
			this.root = root;
		}
		public String getProfile() {
			return profile;
		}
		public void setProfile(String profile) {
			this.profile = profile;
		}
		
		
		
	}
	
}
