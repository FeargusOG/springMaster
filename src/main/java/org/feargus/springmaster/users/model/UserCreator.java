package org.feargus.springmaster.users.model;

import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.mail.Mailer;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.utils.SystemVars;
import org.feargus.springmaster.utils.UtilVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserCreator {
    private UserUtils userUtils;
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserCreator.class);

    public UserCreator() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
	this.userUtils = new UserUtils();
    }

    public void createUserAcc(User userObj) {
	try {
	    this.addUserToDB(userObj);

	    boolean correctPswrd = userUtils.compareHashedPassword(userObj.getEmail(), userObj.getPassword());
	    log.info("Is this the correct password? " + Boolean.toString(correctPswrd));
	} catch (DuplicateKeyException dupEx) {
	    log.info(dupEx.getMessage());
	} catch (Exception ex) {
	    log.info(ex.getMessage());
	}
    }

    private void addUserToDB(User userObj) throws DataAccessException {
	jdbcTemplate.update("INSERT INTO members(userName, email, salt, pswrd) VALUES (?,?,?,?)",
		userObj.getName(), userObj.getEmail(), userObj.getSalt(), userObj.getPassword());
    }

    public void emailUserConfirmation(User userObj) throws NoSuchAlgorithmException {
	Mailer mailSender = new Mailer();
	final String hashedUserEmail = userUtils.hashUserEmail(userObj.getSalt(), userObj.getEmail());
	final String userConfirmationURL = SystemVars.rootUrl + "/accountActivation?userEmail="
		+ userObj.getEmail() + "&token=" + hashedUserEmail;

	mailSender.sendMail(userObj.getEmail(), "User Account Confirmation for feargus.org!",
		"Hi!\n\nThanks for requesting an account for feargus.org! Please follow this link to confirm your account: "
			+ userConfirmationURL);

	log.info("Finished sending a mail asking for account confirmation for user: " + UtilVars.PII_START
		+ userObj.getEmail() + UtilVars.PII_END);
    }
}
