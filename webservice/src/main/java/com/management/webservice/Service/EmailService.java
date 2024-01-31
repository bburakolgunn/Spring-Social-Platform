package com.management.webservice.Service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.webservices.WebServicesProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.management.webservice.Entity.User;
import com.management.webservice.configuration.WebServiceProperties;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	JavaMailSenderImpl mailSender;
	
	private WebServiceProperties webServiceProperties;
	private MessageSource messageSource;
	
	public EmailService(WebServiceProperties webServiceProperties, MessageSource messageSource) {
		super();
		this.webServiceProperties = webServiceProperties;
		this.messageSource = messageSource;
	}


	//EmailService den bir instance oluşturduktan sonra Construct aşaması bittikten sonra
			//bu foksiyonu bu metodu çağır.Construct aşamasının bitmesinin anlamı:
			//Construct edildikten sonra yukarıda value assignment ediliyor,dolayısıyla bütün construct süreci bittikten sonra
			//burası çağırılıyor.
		@PostConstruct 
		public void initialize() {
			this.mailSender = new JavaMailSenderImpl();
			mailSender.setHost(webServiceProperties.getEmail().host());
			mailSender.setPort(webServiceProperties.getEmail().port());
			mailSender.setUsername(webServiceProperties.getEmail().username());
			mailSender.setPassword(webServiceProperties.getEmail().password());
			
			Properties properties = mailSender.getJavaMailProperties();
			properties.put("mail.smtp.starttls.enable", "true");
			
		}
		
		
		String activationEmail = """
				<html>
				<body>
				<h1>${title}</h1>
				<a href ="${url}">${clickHere}</a>
				</body>
				</html>
				""";
		
		
		//Email aktive etme	
		public void sendActivationEmail(String email,String activationtoken) {
			var activationUrl = webServiceProperties.getClient().host() + "/activation/" + activationtoken;
			var title = messageSource.getMessage("webservice.mail.user.created.title",null,LocaleContextHolder.getLocale());
			var clickHere  = messageSource.getMessage("webservice.mail.click.here",null,LocaleContextHolder.getLocale());
			
			
			var mailBody = activationEmail
					.replace("${url}", activationUrl) //
					.replace("${title}",title)
					.replace("${clickHere}",clickHere);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			
			try { //MessagingException hatası olabilir.	
				message.setFrom(webServiceProperties.getEmail().from());
				message.setTo(email);
				message.setSubject(title);
				message.setText(mailBody,true);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			this.mailSender.send(mimeMessage);
		}
		
		public void sendPasswordResetEmail(String email, String passwordResetToken) {
	        String passwordResetUrl = webServiceProperties.getClient().host() + "/password-reset/set?tk=" + passwordResetToken;
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
	        var title = "Reset your password";
	        var clickHere = messageSource.getMessage("webservice.mail.click.here", null, LocaleContextHolder.getLocale());
	        var mailBody = activationEmail.replace("${url}", passwordResetUrl).replace("${title}", title).replace("${clickHere}", clickHere);
	        try {
	          message.setFrom(webServiceProperties.getEmail().from());
	          message.setTo(email); 
	          message.setSubject(title); 
	          message.setText(mailBody, true);
	        } catch (MessagingException e) {
	          e.printStackTrace();
	        }
	        this.mailSender.send(mimeMessage);
	      }
		
	
}
