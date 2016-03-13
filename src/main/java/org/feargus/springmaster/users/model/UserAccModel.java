package org.feargus.springmaster.users.model;

import org.feargus.springmaster.crypto.UserPasswordUtils;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.users.controllers.AccountCreationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
	UserPasswordUtils userPswrdUtil = new UserPasswordUtils();

	try {
	    this.addUserToDB(userObj);

	    boolean correctPswrd = userPswrdUtil.compareUserPassword(userObj.getEmail(), "passw0rd");
	    log.info("Is this the correct password? " + Boolean.toString(correctPswrd));
	} catch (DuplicateKeyException dupEx) {
	    log.info(dupEx.getMessage());
	} catch (Exception ex) {
	    log.info(ex.getMessage());
	}
    }

    private void addUserToDB(AccountCreationForm userObj) throws DataAccessException {
	jdbcTemplate.update("INSERT INTO members(userName, email, salt, pswrd) VALUES (?,?,?,?)",
		userObj.getName(), userObj.getEmail(), userObj.getSalt(), userObj.getPassword());
    }
}
