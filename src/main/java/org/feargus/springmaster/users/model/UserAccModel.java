package org.feargus.springmaster.users.model;

import org.feargus.springmaster.crypto.UserPassword;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.users.controllers.AccountCreationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserAccModel {
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserAccModel.class);

    public UserAccModel() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    }

    public void createUserAcc(AccountCreationForm userObj) {
	UserPassword userPswrd = new UserPassword();

	try {
	    String hashedPassword = userPswrd.hashUserPassword(userObj.getSalt(), userObj.getPassword());
	    log.info("Here is the hashed password before it goes in: " + hashedPassword);
	    userObj.setPassword(hashedPassword);
	    this.addUserToDB(userObj);
	} catch (DuplicateKeyException dupEx) {

	} catch (Exception ex) {

	}

	userPswrd.compareUserPassword(userObj.getEmail(), userObj.getPassword());
    }

    private void addUserToDB(AccountCreationForm userObj) {
	jdbcTemplate.update("INSERT INTO members(userName, email, salt, pswrd) VALUES (?,?,?,?)",
		userObj.getName(), userObj.getEmail(), userObj.getSalt(), userObj.getPassword());
    }
}
