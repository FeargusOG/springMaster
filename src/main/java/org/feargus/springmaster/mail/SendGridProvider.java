package org.feargus.springmaster.mail;

import org.feargus.springmaster.utils.SystemVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.SendGrid;

class SendGridProvider implements MailProvider {
    private static final Logger log = LoggerFactory.getLogger(SendGridProvider.class);

    @Override
    public void sendMail(String to, String from, String subject, String body) throws Exception {

	SendGrid sendgrid = new SendGrid(SystemVars.getInstance().getSENDGRID_USER(), SystemVars
		.getInstance().getSENDGRID_PSWRD());

	SendGrid.Email email = buildEmail(to, from, subject, body);

	SendGrid.Response response = sendgrid.send(email);
	log.info(response.getMessage());
    }

    private SendGrid.Email buildEmail(String to, String from, String subject, String body) {
	SendGrid.Email email = new SendGrid.Email();
	email.addTo(to);
	email.setFrom(from);
	email.setSubject(subject);
	email.setText(body);
	return email;
    }
}
