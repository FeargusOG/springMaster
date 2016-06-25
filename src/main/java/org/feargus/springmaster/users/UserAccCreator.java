package org.feargus.springmaster.users;

import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.db.PostgresqlDataSource;
import org.feargus.springmaster.db.SqlStmts;
import org.feargus.springmaster.mail.Mailer;
import org.feargus.springmaster.utils.SystemVars;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserAccCreator {

    private UserAccUtils userUtils;
    private JdbcTemplate jdbcTemplate;

    public UserAccCreator() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
	this.userUtils = new UserAccUtils();
    }

    public void createUserAcc(CustomUserDetails user) throws NoSuchAlgorithmException {

	/* Set the user's salt and salted password */
	user.setSalt(userUtils.generateSalt());
	user.setPassword(userUtils.hashUserPassword(user.getSalt(), user.getNonSaltedUserPassword()));

	/* Add the user to the DB now */
	this.addUserToDB(user);
    }

    private void addUserToDB(CustomUserDetails user) throws DataAccessException {
	jdbcTemplate.update(SqlStmts.INSERT_USER_SQL, user.getUserHandle(), user.getUserNameEmail(),
		user.getSalt(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
		user.isAccountNonLocked(), user.isCredentialsNonExpired());
    }

    public void emailUserConfirmation(CustomUserDetails userObj) throws Exception {
	Mailer mailSender = new Mailer();
	final String hashedUserEmail = userUtils.hashUserEmail(userObj.getSalt(), userObj.getUserNameEmail());
	final String userConfirmationURL = SystemVars.getInstance().getROOT_URL()
		+ "/accountActivation?userEmail=" + userObj.getUserNameEmail() + "&token=" + hashedUserEmail;
	final String emailBody = "<html><body>Hi!<br /><br /><p>Thanks for requesting an account for feargus.org!</p><p>Please follow this link to confirm your account:"
		+ " <a href=\""
		+ userConfirmationURL
		+ "\">Confirm Account</a>"
		+ "</p>Thanks,<br />Feargus</body></html>";
	final String emailSubject = "User Account Confirmation for feargus.org!";

	mailSender.sendMail(userObj.getUserNameEmail(), SystemVars.getInstance().getADMIN_EMAIL(),
		emailSubject, emailBody);
    }
}
