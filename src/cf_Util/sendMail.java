package cf_Util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
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

import org.testng.annotations.Test;

import cf.Base.TestBase;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


public class sendMail extends TestBase  {
	final static String username = "cloudfabric_operations@canopy-cloud.com";
	final static String password = "3mVSYHrV3nDvH2d!SDUzSxJNzNv!J6";
	
	private static void addAttachment(Multipart multipart, String filename) throws MessagingException
	{
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}

	 @Test
	 public static void sendMailReports()  {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.sendgrid.net");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("reports_automation.atos.net"));
			
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("betkar.sayli@atos.net,charmee.desai@atos.net,anisha.kaulmachama@atos.net,deepika.naravane@atos.net,yash.chouhan@atos.net,harshali.markad@atos.net,p-gunakar.rao@atos.net,prashanth.janardanan@atos.net,shailendra.4.singh@atos.net,jyoti.barthwal@atos.net,amol.tandon@atos.net"));
			//anisha.kaulmachama@atos.net,deepika.naravane@atos.net,rajiv.dixit@atos.net,
			message.setSubject("UI Regression test reports");
			
			 // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("Hi All,"
				+ "\n\n Please find attached regression test reports." + "\n \n Thanks and regards, \n UI Automation Team");

	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = System.getProperty("user.dir") + "\\test-output\\AutomationReport.html";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	        // messageBodyPart.setFileName(filename);
	         messageBodyPart.setFileName("AutomationReport.html");
	         multipart.addBodyPart(messageBodyPart);
	         //2 nd attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename1 = System.getProperty("user.dir") + "\\CustomReports\\TestResultsData.xls";
	         DataSource source1 = new FileDataSource(filename1);
	         messageBodyPart.setDataHandler(new DataHandler(source1));
	         messageBodyPart.setFileName("UI_Regression.xls");
	         
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);
			Transport.send(message);

			TestLog.info("Reports sent on mail");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	 
	 @Test
	 public static void sendMailLog()  {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.sendgrid.net");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("reports_automation.atos.net"));
			
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("betkar.sayli@atos.net,charmee.desai@atos.net,harshali.markad@atos.net,yash.chouhan@atos.net"));
			message.setSubject("UI Regression test log file");
			
			 // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("Hi All,"
				+ "\n\n Please find attached regression test logs." + "\n \n Thanks and regards, \n UI Automation Team");

	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = System.getProperty("user.dir") + "\\log\\Application.log";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	        // messageBodyPart.setFileName(filename);
	         messageBodyPart.setFileName("ApplicationLog.txt");
	         multipart.addBodyPart(messageBodyPart);
	         
	         // Send the complete message parts
	         message.setContent(multipart);
			Transport.send(message);

			TestLog.info("Logs sent on mail to automation team");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
