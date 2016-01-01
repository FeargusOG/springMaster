package org.feargus.springmaster.invites.controllers;

import org.feargus.springmaster.utils.UtilVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmInviteCtrl {

    private static final Logger log = LoggerFactory.getLogger(ConfirmInviteCtrl.class);

    @RequestMapping(value = "/confirmInvite", method = RequestMethod.GET)
    public String confirmInvite(@RequestParam(value = "userEmail", required = true) String userEmail,
	    @RequestParam(value = "token", required = true) String token, Model model) {

	log.info("Confirmed an invite for: " + UtilVars.PII_START + userEmail + UtilVars.PII_END);
	model.addAttribute("userEmail", userEmail);

	return "confirmInvite";
    }

}
