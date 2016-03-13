package org.feargus.springmaster.users.controllers;

import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.users.model.UserUtils;
import org.feargus.springmaster.utils.UtilVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountActivationCtrlr {
    private static final Logger log = LoggerFactory.getLogger(AccountActivationCtrlr.class);

    @RequestMapping(value = "/accountActivation", method = RequestMethod.GET)
    public String getAccountActivation(@RequestParam(value = "userEmail", required = true) String userEmail,
	    @RequestParam(value = "token", required = true) String token, Model model) {

	boolean correctToken = false;
	/* Compare this email and token to the DB entries */
	UserUtils userUtils = new UserUtils();
	try {
	    correctToken = userUtils.compareHashedEmail(userEmail, token);
	} catch (NoSuchAlgorithmException e) {
	    // TODO Some error handling here!!
	    log.info("Failed to activate account for user " + UtilVars.PII_START + userEmail
		    + UtilVars.PII_END + ".");
	    log.info(e.getMessage());
	}

	if (correctToken) {
	    userUtils.activateUser(userEmail);
	    log.info("Activated account for user " + UtilVars.PII_START + userEmail + UtilVars.PII_END + ".");
	} else {
	    log.info("Failed to activate account for user " + UtilVars.PII_START + userEmail
		    + UtilVars.PII_END + ".");
	}

	model.addAttribute("isSuccess", correctToken);
	return "accountActivation";
    }
}
