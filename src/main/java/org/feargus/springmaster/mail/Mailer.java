package org.feargus.springmaster.mail;

import org.feargus.springmaster.utils.SystemVars;

public class Mailer implements MailProvider {
    private MailProvider specificMailProvider = null;
    private String systemBuild = null;

    public Mailer() throws Exception {
	/* Find out whether we want the prod or test mailer */
	systemBuild = SystemVars.getInstance().getSYSTEM_BUILD();

	if (systemBuild.equals("production")) {
	    specificMailProvider = new SendGridProvider();
	} else if (systemBuild.equals("test")) {
	    specificMailProvider = new GmailProvider();
	} else {
	    throw new Exception("Invalid Build System specified for Mailer: " + systemBuild);
	}
    }

    @Override
    public void sendMail(String to, String from, String subject, String body) throws Exception {
	this.specificMailProvider.sendMail(to, from, subject, body);
    }
}