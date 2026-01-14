package com.example.ecommerce.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Admins;
import com.example.ecommerce.model.Users;

@Service
public class MailService {
	
	private SimpleMailMessage message;
	private JavaMailSender sender;

	public MailService(SimpleMailMessage message,JavaMailSender sender) {
		this.message=message;
		this.sender=sender;
	}
	
	protected void accountCreationMail(Users user) {
		String subject = "Welcome to AzCart – Your Account Has Been Created Successfully 🎉";
		String receiver = user.getEmail();
		String body = "Dear "+user.getName()+",\r\n"
				+ "\r\n"
				+ "Welcome to AzCart!\r\n"
				+ "\r\n"
				+ "We’re excited to inform you that your AzCart account has been created successfully using this email address.\r\n"
				+ "\r\n"
				+ "You can now log in and start exploring our platform to discover products, offers, and a seamless shopping experience.\r\n"
				+ "\r\n"
				+ "If you did not create this account or believe this was a mistake, please contact our support team immediately.\r\n"
				+ "\r\n"
				+ "For security reasons, never share your login credentials with anyone.\r\n"
				+ "\r\n"
				+ "Thank you for choosing AzCart.\r\n"
				+ "We’re glad to have you with us!\r\n"
				+ "\r\n"
				+ "Best regards,  \r\n"
				+ "AzCart Support Team  \r\n"
				+ "Email: azcart.noreply@gmail.com\r\n"
				+ "";
		message.setSubject(subject);
		message.setText(body);
		message.setTo(receiver);
		sender.send(message);
	}
	
	protected void forgotPasswordMail(Users user,String token) {
		String subject = "AZCART – Reset Your Password";
		String receiver =  user.getEmail();
		String url = "http://localhost:5173/reset-password?token="+token;
		String body = "Hello "+user.getName()+",\r\n"
				+ "\r\n"
				+ "We received a request to reset your AZCART account password.\r\n"
				+ "\r\n"
				+ "To create a new password, please click the link below:\r\n"
				+ url+"\r\n"
				+ "\r\n"
				+ "For your security, this link will expire in 15 minutes and can be used only once.\r\n"
				+ "\r\n"
				+ "If you did not request a password reset, please ignore this email. Your account will remain secure.\r\n"
				+ "\r\n"
				+ "Password requirements:\r\n"
				+ "• Minimum 8 characters\r\n"
				+ "• At least 1 uppercase letter\r\n"
				+ "• At least 1 lowercase letter\r\n"
				+ "• At least 1 number\r\n"
				+ "• At least 1 special character\r\n"
				+ "\r\n"
				+ "Thank you for choosing AZCART.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "AZCART Support Team\r\n"
				+ "azcart.noreply@gmail.com\r\n"
				+ "";
		
		message.setSubject(subject);
		message.setText(body);
		message.setTo(receiver);
		sender.send(message);
	}
	
	protected void forgotPasswordMail(Admins admin,String token) {
		String subject = "AZCART – Reset Your Password";
		String receiver =  admin.getEmail();
		String url = "http://localhost:5173/reset-password?token="+token;
		String body = "Hello "+admin.getAdminName()+",\r\n"
				+ "\r\n"
				+ "We received a request to reset your AZCART account password.\r\n"
				+ "\r\n"
				+ "To create a new password, please click the link below:\r\n"
				+ url+"\r\n"
				+ "\r\n"
				+ "For your security, this link will expire in 15 minutes and can be used only once.\r\n"
				+ "\r\n"
				+ "If you did not request a password reset, please ignore this email. Your account will remain secure.\r\n"
				+ "\r\n"
				+ "Password requirements:\r\n"
				+ "• Minimum 8 characters\r\n"
				+ "• At least 1 uppercase letter\r\n"
				+ "• At least 1 lowercase letter\r\n"
				+ "• At least 1 number\r\n"
				+ "• At least 1 special character\r\n"
				+ "\r\n"
				+ "Thank you for choosing AZCART.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "AZCART Support Team\r\n"
				+ "azcart.noreply@gmail.com\r\n"
				+ "";
		
		message.setSubject(subject);
		message.setText(body);
		message.setTo(receiver);
		sender.send(message);
	}
}
