package org.asu.ss.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public void sendMail(String to,String pass)
	{
		final String username = "ssyaoasu@gmail.com";
		final String password = "SS@123456789";
		System.out.println("Sending mail");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ssyaoasu@gmail.com"));
			System.out.println("to"+to);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Password Reset SS");
			message.setText("Hey here is your temporary password " + pass);
			Transport.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
