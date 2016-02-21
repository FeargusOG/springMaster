package org.feargus.springmaster.users.controllers;

import javax.validation.Valid;

import org.feargus.springmaster.invites.controllers.RequestInviteCtrlr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountCreationCtrlr {

    private static final Logger log = LoggerFactory.getLogger(RequestInviteCtrlr.class);

    @RequestMapping(value = "/accountCreation", method = RequestMethod.GET)
    public String getAccountCreation(AccountCreationForm accForm) {
	return "accountCreation";
    }

    @RequestMapping(value = "/accountCreation", method = RequestMethod.POST)
    public String postAccountCreation(@Valid AccountCreationForm accForm, BindingResult bindingResult) {

	if (bindingResult.hasErrors()) {
	    return "accountCreation";
	}

	log.info("Creating an account for user: " + accForm.getName());

	return "redirect:/home";
    }
}
