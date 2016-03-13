package org.feargus.springmaster.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.model.PostgresqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserPasswordUtils {
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserPasswordUtils.class);

    public UserPasswordUtils() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    }

    public String hashUserPassword(String salt, String pswrd) throws NoSuchAlgorithmException {
	String generatedPassword = null;
	log.info("Salt: '" + salt + "' Pswrd: " + pswrd);
	try {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(salt.getBytes());
	    byte[] bytes = md.digest(pswrd.getBytes());
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
		sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    generatedPassword = sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    log.info(e.getMessage());
	}
	return generatedPassword;
    }

    public boolean compareUserPassword(String userEmail, String inputHashedPswrd) throws DataAccessException,
	    NoSuchAlgorithmException {
	String dbHashedPswrd = this.selectPasswordFromDB(userEmail);

	if (inputHashedPswrd.equals(dbHashedPswrd)) {
	    return true;
	} else {
	    return false;
	}
    }

    private String selectPasswordFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject("SELECT pswrd from members WHERE email= ?",
		new Object[] { userEmail }, String.class);
    }

    private String selectSaltFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject("SELECT salt from members WHERE email= ?",
		new Object[] { userEmail }, String.class);
    }
}