package com.task.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired // 3 features for mail 1) Java mail sender 2) mail trap , send grid
	private JavaMailSender mailSender;
	
	public void setResetEmail(String to,String token) {
		String resetToken="http://localhost:8080/api/Authentication/reset_password?token="+token;
		
		SimpleMailMessage message =new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject("Reset your Password");
		message.setText("Click the link to reset your password : \n"+resetToken);
		
		mailSender.send(message);
	}
}
