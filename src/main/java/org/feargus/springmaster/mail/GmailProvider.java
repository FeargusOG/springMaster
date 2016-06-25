package org.feargus.springmaster.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.feargus.springmaster.utils.SystemVars;

import com.sun.mail.smtp.SMTPTransport;

class GmailProvider implements MailProvider {
    // private static final Logger log =
    // LoggerFactory.getLogger(GmailProvider.class);
    private Session mailSession;
    private final String FROM_PWRD = SystemVars.getInstance().getADMIN_EMAIL_PSWRD();
    private final String SMTP_HOST = "smtp.gmail.com";

    public GmailProvider() {
	// Build the Session
	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	this.mailSession = Session.getInstance(setMailProps(), null);
    }

    @Override
    public void sendMail(String to, String from, String subject, String body) throws Exception {

	// Build the message
	final MimeMessage msg = buildMimeMsg(to, from, subject, body);

	// Send the message
	sendMimeMsg(from, msg);

    }

    private MimeMessage buildMimeMsg(String to, String from, String subj, String body)
	    throws MessagingException {

	final MimeMessage msg = new MimeMessage(this.mailSession);

	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	msg.setFrom(new InternetAddress(from));
	msg.setSubject(subj);
	msg.setText(body, "utf-8");
	msg.setSentDate(new Date());

	return msg;
    }

    private void sendMimeMsg(String from, MimeMessage msg) throws Exception {
	SMTPTransport transport = (SMTPTransport) this.mailSession.getTransport("smtps");
	transport.connect(this.SMTP_HOST, from, this.FROM_PWRD);
	transport.sendMessage(msg, msg.getAllRecipients());
	transport.close();
    }

    private Properties setMailProps() {
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

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
