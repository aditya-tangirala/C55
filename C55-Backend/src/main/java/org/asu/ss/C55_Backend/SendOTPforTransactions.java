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

public class SendOTPforTransactions extends OneTime {
	OneTime otp = new OneTime();
	String[] response = new String[2];

	public String[] mailtheOTPforTransactions(TempTransaction temptransaction, String fieldtobeUpdated) 
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
			System.out.println("Reached \n");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ssyaoasu@gmail.com"));
			System.out.println(fieldtobeUpdated+" is the field to be updated.");
			if(fieldtobeUpdated.equals("Mobile"))
			{
				System.out.println(temptransaction.getMobile_carrier()+" is the field to be updated.");
				System.out.println(temptransaction.getMobile()+" is the field to be updated.");
				if(temptransaction.getMobile_carrier().equals("AT&T"))
				{
					System.out.println("Reached ");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getMobile()+"@txt.att.net"));
					response[0] = "Mailed the OTP to " + temptransaction.getMobile();
				}
				else if(temptransaction.getMobile_carrier().equals("T-Mobile"))
				{
					System.out.println("Reached ");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getMobile()+"@tmomail.net"));
					response[0] = "Mailed the OTP to " + temptransaction.getMobile();
				}
				else if(temptransaction.getMobile_carrier().equals("Verizon"))
				{
					System.out.println("Reached ");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getMobile()+"@vtext.com"));
					response[0] = "Mailed the OTP to " + temptransaction.getMobile();
				}
				else if(temptransaction.getMobile_carrier().equals("Sprint"))
				{
					System.out.println("Reached ");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getMobile()+"@messaging.sprintpcs.com"));
					response[0] = "Mailed the OTP to " + temptransaction.getMobile();
				}
				else if(temptransaction.getMobile_carrier().equals("US Cellular"))
				{
					System.out.println("Reached ");
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getMobile()+"@email.uscc.net"));
					response[0] = "Mailed the OTP to " + temptransaction.getMobile();
				}
				//Will use a switch case here
			}
			else if(fieldtobeUpdated.equals("Email"))
			{
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(temptransaction.getEmail()));
					response[0] = "Mailed the OTP to " + temptransaction.getEmail();
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