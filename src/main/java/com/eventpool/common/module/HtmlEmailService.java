package com.eventpool.common.module;


import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlEmailService
{
		@Value("$EVENT_POOL{mail.from}")
		private String from;

		@Value("$EVENT_POOL{mail.host}")
		private String host;

		@Value("$EVENT_POOL{mail.username}")
		private String username;

		@Value("$EVENT_POOL{mail.password}")
		private String password;

		protected static final Logger logger = LoggerFactory.getLogger(HtmlEmailService.class);
		public Boolean sendMail(List<String> toList,String subject,String body,List<String> ccList,EmailAttachment attachment) {
				// Recipient's email ID needs to be mentioned.
			  // String to = "ramuenugurthi@gmail.com";
			
			   // Get system properties
			   Properties props = System.getProperties();
			
			   // Setup mail server
			   props.setProperty("mail.smtp.host", host);
			   
			   props.put("mail.smtp.auth", "true");
		//	   props.put("mail.smtp.starttls.enable", "true");
			   props.put("mail.smtp.host", host);
			   //props.put("mail.smtp.port", "587");
			   props.put("mail.smtp.port", "25");
			
			   final PasswordAuthentication passwordAuthentication = new PasswordAuthentication(this.username, this.password);
			   Authenticator authenticator = new javax.mail.Authenticator() {
					protected final PasswordAuthentication getPasswordAuthentication() {
						return passwordAuthentication;
					}
				  };
			// Get the default Session object.
			   Session session = Session.getDefaultInstance(props, authenticator);
			
			   try{
			      // Create a default MimeMessage object.
			      MimeMessage message = new MimeMessage(session);
			
			      // Set From: header field of the header.
			      message.setFrom(new InternetAddress(from));
			
			      // Set To: header field of the header.
			      if(toList!=null && toList.size()>0){
			    	  for(String to:toList){
					      message.addRecipient(Message.RecipientType.TO,
					                               new InternetAddress(to));
			    	  }
			      }
			      
			      if(ccList!=null && ccList.size()>0){
			    	  for(String to:ccList){
					      message.addRecipient(Message.RecipientType.CC,
					                               new InternetAddress(to));
			    	  }
			      }
			
			      // Set Subject: header field
			      message.setSubject(subject);
			
			      // Send the actual HTML message, as big as you like
			      message.setContent(body,"text/html" );
			      
			      MimeBodyPart messageBodypart = new MimeBodyPart();
	                messageBodypart.setText(body);
	                
	                Multipart mp = new MimeMultipart();
	                mp.addBodyPart(messageBodypart);
	                
	                if(attachment!=null ){
	                byte[] bytes = attachment.getAttachment().toByteArray();
	                DataSource source = new ByteArrayDataSource(bytes, "application/pdf");
	                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
	                attachmentBodyPart.setDataHandler(new DataHandler(source));
	                attachmentBodyPart.setFileName(attachment.getAttachmentName());
	                mp.addBodyPart(attachmentBodyPart);
	                }
	                	message.setContent(mp);
			
			      // Send message
			      Transport.send(message);
			      logger.info("mail sent successfully to :"+toList);
			      return true;
			   }catch (MessagingException mex) {
				   logger.error("error in sending mail",mex);
				   return false;
			   }
		}
}
