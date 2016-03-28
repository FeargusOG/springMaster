package org.feargus.springmaster.users.model;

import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.crypto.StringHasher;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserUtils {
    private JdbcTemplate jdbcTemplate;
    private StringHasher stringHasher;
    private static final Logger log = LoggerFactory.getLogger(UserUtils.class);

    public UserUtils() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
	this.stringHasher = new StringHasher();
    }

    public String hashUserPassword(String salt, String pswrd) throws NoSuchAlgorithmException {
	String hashedPassword = stringHasher.hashThisString(combineSalt(salt, pswrd));
	log.info("HashedPassword: " + hashedPassword);
	return hashedPassword;
    }

    public String hashUserEmail(String salt, String userEmail) throws NoSuchAlgorithmException {
	String hashedEmail = stringHasher.hashThisString(combineSalt(salt, userEmail));
	log.info("HashedEmail: " + hashedEmail);
	return hashedEmail;
    }

    public boolean compareHashedPassword(String userEmail, String inputHashedPswrd)
	    throws DataAccessException, NoSuchAlgorithmException {
	String dbHashedPswrd = selectPasswordFromDB(userEmail);

	if (inputHashedPswrd.equals(dbHashedPswrd)) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean compareHashedEmail(String userEmail, String inputHashedEmail)
	    throws NoSuchAlgorithmException {
	String dbSalt = selectSaltFromDB(userEmail);
	String hashedEmail = stringHasher.hashThisString(combineSalt(dbSalt, userEmail));

	if (inputHashedEmail.equals(hashedEmail)) {
	    return true;
	} else {
	    return false;
	}
    }

    public void activateUser(String userEmail) {
	jdbcTemplate.update("UPDATE members SET active='t' WHERE email=?", userEmail);
    }

    private String selectPasswordFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject("SELECT pswrd from members WHERE email= ?",
		new Object[] { userEmail }, String.class);
    }

    private String selectSaltFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject("SELECT salt from members WHERE email= ?",
		new Object[] { userEmail }, String.class);
    }

    private String combineSalt(String salt, String inputString) {
	return salt + inputString;
    }
}