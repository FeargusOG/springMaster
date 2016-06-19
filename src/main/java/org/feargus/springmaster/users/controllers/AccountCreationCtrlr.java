package org.feargus.springmaster.users.controllers;

import javax.validation.Valid;

import org.feargus.springmaster.users.CustomUserDetails;
import org.feargus.springmaster.users.UserAccCreator;
import org.feargus.springmaster.users.UserAccUtils;
import org.feargus.springmaster.users.UserRoleCreator;
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
    public String getAccountCreation(CustomUserDetails userDetails) {
	/*
	 * The form will look for the class name CustomUserDetails with the
	 * first letter lowercase - customUserDetails. That's how it
	 * automatically finds the binding. Otherwise, I think you can specify
	 * modelAttribute if you want a different name to be used for the
	 * variable in the form.
	 */

	return "accountCreation";
    }

    @RequestMapping(value = "/accountCreation", method = RequestMethod.POST)
    public String postAccountCreation(@Valid CustomUserDetails userObj, BindingResult bindingResult) {

	/* Check that the userObj had no errors */
	if (bindingResult.hasErrors()) {
	    return "accountCreation";
	}

	/* Create an account for the user */
	UserAccCreator userAccCreator = new UserAccCreator();
	try {
	    userAccCreator.createUserAcc(userObj);
	    log.info("Sucessfuly created an account for: " + userObj.toString());
	} catch (Exception e) {
	    log.info("Failed to create an account for: " + userObj.toString());
	    log.info(e.getMessage());
	    return "redirect:/error";// TODO Error page or something here.....
	}

	/* Add a USER role for this user */
	UserRoleCreator userRoleCreator = new UserRoleCreator();
	try {
	    userRoleCreator.addRoleForUser(userObj.getUserNameEmail(), UserAccUtils.ROLE_USER);
	    log.info("Succesfully added a User role for account: " + userObj.toString());
	} catch (Exception e) {
	    log.info("Failed to add a User role for account: " + userObj.toString());
	    e.printStackTrace();
	    // log.info(e.getMessage());
	    return "redirect:/error";// TODO Error page or something here.....
	}

	/* Email the user to confirm their account */
	try {
	    userAccCreator.emailUserConfirmation(userObj);
	    log.info("Sucessfuly emailed user for account confirm: " + userObj.toString());
	} catch (Exception e) {
	    log.info("Failed to email user for account confirm: " + userObj.toString());
	    log.info(e.getMessage());
	    return "redirect:/error";// TODO Error page or something here.....
	}

	return "redirect:/index";
    }
}
