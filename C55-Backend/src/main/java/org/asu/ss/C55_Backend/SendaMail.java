package org.asu.ss.C55_Backend;

import java.util.Properties;
import org.asu.ss.model.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendaMail extends OneTime {
	OneTime otp = new OneTime();
	String[] response = new String[2];

	public String[] mailtheOTP(TempExternalUser tempexternaluser, String fieldtobeUpdated) 
	{
		final String username = "ssyaoasu@gmail.com";
		final String password = "SS@123456789";
		
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

		try 
		{
			System.out.println("Reached OTP MODULE\n");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ssyaoasu@gmail.com"));
			System.out.println(fieldtobeUpdated+" is the field to be updated.");
			if(fieldtobeUpdated.equals("Mobile"))
			{
				System.out.println(tempexternaluser.getMobile_carrier()+" is the field to be updated.");
				System.out.println(tempexternaluser.getMobile()+" is the field to be updated.");
				if(tempexternaluser.getMobile_carrier().equals("AT&T"))
				{
					System.out.println("Reached AT&T");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getMobile()+"@txt.att.net"));
					response[0] = "Mailed the OTP to " + tempexternaluser.getMobile();
				} else if(tempexternaluser.getMobile_carrier().equals("Verizon"))
				{
					System.out.println("Reached Verizon");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getMobile()+"@vtext.com"));
					response[0] = "Mailed the OTP to " + tempexternaluser.getMobile();
				}else if(tempexternaluser.getMobile_carrier().equals("T-Mobile"))
				{
					System.out.println("Reached T-Mobile");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getMobile()+"@tmomail.net"));
					response[0] = "Mailed the OTP to " + tempexternaluser.getMobile();
				}else if(tempexternaluser.getMobile_carrier().equals("Sprint"))
				{
					System.out.println("Reached Sprint");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getMobile()+"@messaging.sprintpcs.com"));
					response[0] = "Mailed the OTP to " + tempexternaluser.getMobile();
				}else if(tempexternaluser.getMobile_carrier().equals("MTS"))
				{
					System.out.println("Reached MTS");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getMobile()+"@text.mtsmobility.com"));
					response[0] = "Mailed the OTP to " + tempexternaluser.getMobile();
				}
			}
			else if(fieldtobeUpdated.equals("Email"))
			{
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tempexternaluser.getEmail()));
					response[0] = "Mailed the OTP to " + tempexternaluser.getEmail();
			}
			else
				System.out.println("Purpose not mentioned correctly!");
			
			message.setSubject("Testing Subject");
			String otpValue = otp.testTOTP();
			message.setText("Hey here is your SS OTP - " + otpValue);

			Transport.send(message);
		    response[1] = otpValue;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return response;
		
	}

}