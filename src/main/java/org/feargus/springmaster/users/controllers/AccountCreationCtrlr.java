package org.feargus.springmaster.users.controllers;

import javax.validation.Valid;

import org.feargus.springmaster.invites.controllers.RequestInviteCtrlr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountCreationCtrlr {

    private static final Logger log = LoggerFactory.getLogger(RequestInviteCtrlr.class);

    @RequestMapping(value = "/accountCreation", method = RequestMethod.GET)
    public String getAccountCreation(Model model) {
	model.addAttribute("accountCreation", new AccountCreationForm());
	return "accountCreation";
    }

    @RequestMapping(value = "/accountCreation", method = RequestMethod.POST)
    public String checkPersonInfo(@Valid AccountCreationForm accForm, BindingResult bindingResult, Model model) {

	if (bindingResult.hasErrors()) {
	    return "accountCreation";
	} else {
	    model.addAttribute(accForm);
	}

	log.info("\n\nIn POST." + accForm.getName() + " - " + Integer.toString(accForm.getAge()) + "\n\n");

	return "redirect:/home";
    }

}
