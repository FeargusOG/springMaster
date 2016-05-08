package org.feargus.springmaster.users.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.feargus.springmaster.users.User;
import org.feargus.springmaster.users.UserCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountCreationCtrlr {

    private static final Logger log = LoggerFactory.getLogger(AccountCreationCtrlr.class);

    @RequestMapping(value = "/accountCreation", method = RequestMethod.GET)
    public String getAccountCreation(User accForm) {
	log.info("Made it to here in get...");
	return "accountCreation";
    }

    @RequestMapping(value = "/accountCreation", method = RequestMethod.POST)
    public String postAccountCreation(@Valid User userObj, BindingResult bindingResult) {
	UserCreator userCreator = new UserCreator();
	log.info("Made it to here in post...");
	if (bindingResult.hasErrors()) {
	    return "accountCreation";
	}

	/* Add the user to the DB */
	log.info("Creating an account: " + userObj.toString());
	userCreator.createUserAcc(userObj);

	/* Email the user to confirm their account */
	try {
	    userCreator.emailUserConfirmation(userObj);
	} catch (NoSuchAlgorithmException e) {
	    log.info(e.getMessage());
	    // TODO Error page or something here.....
	}

	return "redirect:/index";
    }
}
