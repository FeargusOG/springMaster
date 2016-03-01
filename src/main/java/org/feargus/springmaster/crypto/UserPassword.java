package org.feargus.springmaster.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.model.PostgresqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserPassword {
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserPassword.class);

    public UserPassword() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    }

    public String hashUserPassword(String salt, String pswrd) throws NoSuchAlgorithmException {
	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	byte[] hash = digest.digest(pswrd.getBytes(StandardCharsets.UTF_8));
	return String.format("%064x", new java.math.BigInteger(1, hash));
    }

    public void compareUserPassword(String userEmail, String inputPswrd) {
	String hashedPswrd = this.selectPasswordFromDB(userEmail);
	log.info("HERE IS THE HASHED PASSWORD: " + hashedPswrd);
    }

    private String selectPasswordFromDB(String userEmail) {
	return (String) this.jdbcTemplate.queryForObject("SELECT pswrd from members WHERE email= ?",
		new Object[] { userEmail }, String.class);
    }
}
