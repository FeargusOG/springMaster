package org.feargus.springmaster.users.model;

import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.users.controllers.AccountCreationForm;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserAccModel {
    private JdbcTemplate jdbcTemplate;

    public UserAccModel() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    }

    public void createUserAcc(AccountCreationForm userObj) {

	try {
	    this.addUserToDB();
	} catch (DuplicateKeyException dupEx) {

	} catch (Exception ex) {

	}
    }

    private void addUserToDB() {
	// jdbcTemplate.update("INSERT INTO invitetokens(token, email) VALUES (?,?)",
	// uniqueToken, userEmail);
    }
}
