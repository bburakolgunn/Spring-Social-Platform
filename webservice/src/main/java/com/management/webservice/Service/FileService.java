package com.management.webservice.Service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import com.management.webservice.Service.impl.FileServiceImpl;
import com.management.webservice.configuration.WebServiceProperties;


@Service
public class FileService implements FileServiceImpl {
	//Kullanıcının image string'ini file olarak kaydetmesi işlemi yapılacak.Cevaben de o dosyanın
		//adını dönecek,daha sonra database'de o dosyanın adını tutmuş olunacak.
	
	private WebServiceProperties webServiceProperties;
	
	public FileService(WebServiceProperties webServiceProperties) {
		super();
		this.webServiceProperties = webServiceProperties;
	}
	
	Tika tika = new Tika();

	//Base64 String file'a çevrilir.
	@Override
	public String saveBase64StringAsFile(String image) {
		String filename = UUID.randomUUID().toString();
		
		Path path = getProfileImagePath(filename);
		try {
			OutputStream outputStream  = new FileOutputStream(path.toFile()); 
			outputStream.write(decodedImage(image));
			outputStream.close();
			return filename;
		} catch (IOException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
			
		}
		return null;
		
		
	}

	@Override
	public String detectType(String value) {
		return tika.detect(decodedImage(value));
		
	}

	
	private byte[] decodedImage(String encodedImage) {
		return Base64.getDecoder().decode(encodedImage.split(",")[1]);
		
	}

	@Override
	public void deleteProfileImage(String image) {
		if(image == null) return;
		Path path = getProfileImagePath(image);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	private Path getProfileImagePath(String filename) {
		return Paths.get(webServiceProperties.getStorage().getRoot(), webServiceProperties.getStorage().getProfile(),filename);
	}
	
	
	
	
	
	

}
