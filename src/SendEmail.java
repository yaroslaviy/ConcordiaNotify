import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	public static void sendMail(Course c, String to){
		String user = "concordianotify@gmail.com";
		String pass = "PasswordNotify1";
		String host = "smtp.gmail.com";
		
		Properties prop = new Properties();
		prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.required", "true");
        
       
		Session ses = Session.getDefaultInstance(prop,     new javax.mail.Authenticator() {    
	           protected PasswordAuthentication getPasswordAuthentication() {    
	               return new PasswordAuthentication(user,pass);  
	               }    
	              });    
		
		try {
			
			MimeMessage msg = new MimeMessage(ses);
			msg.setFrom(new InternetAddress(user));
			InternetAddress[] address = {new InternetAddress(to)};
	        msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("Your " + c.toString() + "Concordia Class  Was Cancelled");
			msg.setText("Dear User, " + '\n' + "Class " + c.toString() + " was cancelled today according to Concordia website." + '\n' + "Thanks for using our notifier!" );
			System.out.println("debug");
			Transport.send(msg);
			System.out.println("message sent successfully"); 
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}
}
