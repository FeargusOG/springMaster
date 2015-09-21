package org.feargus.springmaster.mail;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	private final String FROM = "feargusorg@gmail.com";
	private final String FROM_PWRD = "FeargusOrgPa88w0rd!";
	private final String SMTP_HOST = "smtp.gmail.com";
	private Session mailSession;
	
	public Mailer(){
		//Build the Session
    	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        this.mailSession = Session.getInstance(setMailProps(), null);
	}

    public void sendMail(String to, String subj, String body){
        try{
        	//Build the Msg
            final MimeMessage msg = buildMimeMsg(to, subj, body);
            
            //Send the Msg
            sendMimeMsg(msg);
            
        } catch (Exception ex){
        	ex.printStackTrace();
        }
        
    }
    
    private MimeMessage buildMimeMsg(String to, String subj, String body) throws MessagingException{
    	final MimeMessage msg = new MimeMessage(this.mailSession);
    	
        msg.setFrom(new InternetAddress(this.FROM));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subj);
        msg.setText(body, "utf-8");
        msg.setSentDate(new Date());
        
		return msg;
    }
    
    private void sendMimeMsg(MimeMessage msg) throws MessagingException{
    	SMTPTransport transport = (SMTPTransport)this.mailSession.getTransport("smtps");
        transport.connect(this.SMTP_HOST, this.FROM, this.FROM_PWRD);
        transport.sendMessage(msg, msg.getAllRecipients());      
        transport.close();
    }
    
    private Properties setMailProps(){
    	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    	
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        
        return props;
    }
}