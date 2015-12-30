package org.feargus.springmaster.invites.controllers;

import org.feargus.springmaster.mail.Mailer;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.uniqueids.UniqueTokenGenerator;
import org.feargus.springmaster.utils.SystemVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateInviteCtrlr {
    private static final Logger log = LoggerFactory.getLogger(GenerateInviteCtrlr.class);

    @RequestMapping(value = "/generateInvite")
    public String generateInvite(@RequestParam(value = "userEmail", required = true) String userEmail,
	    Model model) {
	final String uniqueToken = new UniqueTokenGenerator().getUniqueToken();
	String errMsg = null;//

	try {
	    this.insertInviteInDB(uniqueToken, userEmail);
	} catch (DuplicateKeyException e) {
	    errMsg = "Tried to insert invite for email: " + userEmail
		    + ", but they are already invited! Resending invite email to user...";
	    log.info(errMsg);

	} catch (Exception ex) {
	    log.info("General Exception");
	    ex.printStackTrace();
	    // TODO: Return a 500 error page here.
	}

	this.emailUserInvite(userEmail, uniqueToken);

	model.addAttribute("errMsg", errMsg);
	model.addAttribute("userEmail", userEmail);

	return "generateInvite";
    }

    private void insertInviteInDB(String uniqueToken, String userEmail) throws DuplicateKeyException,
	    Exception {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	JdbcTemplate jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
	jdbcTemplate.update("INSERT INTO invitetokens(token, email) VALUES (?,?)", uniqueToken, userEmail);
    }

    private void emailUserInvite(String userEmail, String token) {
	final String inviteConfirmationUrl = SystemVars.rootUrl + "/confirmInvite?userEmail=" + userEmail
		+ "&token=" + token;
	Mailer mailSender = new Mailer();
	mailSender.sendMail(userEmail, "Invite request accepted for feargus.org!",
		"Hi!\n\nThanks for requesting an invite at feargus.org! Please follow this link to confirm your invite: "
			+ inviteConfirmationUrl);

	log.info("Finished sending a mail extending an invite for: " + userEmail);
    }

}
