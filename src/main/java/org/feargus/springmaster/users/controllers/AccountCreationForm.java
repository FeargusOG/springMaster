package org.feargus.springmaster.users.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.Size;

import org.feargus.springmaster.crypto.UniqueTokenGenerator;
import org.feargus.springmaster.crypto.UserPasswordUtils;
import org.feargus.springmaster.utils.UtilVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountCreationForm {

    private String salt;
    private String password = null;
    private UserPasswordUtils userPswrdUtil;
    private static final Logger log = LoggerFactory.getLogger(AccountCreationForm.class);

    @Size(min = 2, max = 255)
    private String name = null;

    @Size(min = 3, max = 255)
    private String email = null;

    public AccountCreationForm() {
	this.userPswrdUtil = new UserPasswordUtils();
	this.salt = new UniqueTokenGenerator().getUniqueToken();
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	try {
	    String hashedPassword = userPswrdUtil.hashUserPassword(salt, password);
	    this.password = hashedPassword;
	} catch (NoSuchAlgorithmException e) {
	    log.info(e.getMessage());
	}

    }

    public String getSalt() {
	return salt;
    }

    public String toString() {
	return "Person(" + UtilVars.PII_START + "Name: " + this.name + ", Email: " + this.email
		+ UtilVars.PII_END + ")";
    }
}