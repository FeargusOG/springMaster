package org.feargus.springmaster.invites.controllers;

import org.feargus.springmaster.Application;
import org.feargus.springmaster.invites.models.InviteRequest;
import org.feargus.springmaster.mail.Mailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestInviteCtrlr {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@RequestMapping(value="/requestInvite", method=RequestMethod.GET)
    public String greetingForm(Model model) {
		model.addAttribute("requestInvite", new InviteRequest());
        return "requestInvite";
    }

	@RequestMapping(value="/requestInvite", method=RequestMethod.POST)
	public String requestInvite(@RequestParam(value="projectNameHere", required=true) String projectNameHere, @ModelAttribute InviteRequest invite, Model model){
		String token = "blaher1";
		
		model.addAttribute("requestInvite", invite);
		
		log.info(invite.getUserEmail());
		
    	Mailer mailSender = new Mailer();
    	mailSender.sendMail("feargusorg@gmail.com", "Invite Request for"+invite.getProjectName(), "I would like to join please! :) User = "+invite.getUserEmail()+" Project = "+invite.getProjectName()+" Token = "+token);
    	
    	log.info("Finished sending a mail....");
		
		return "requestInvite";
	}
}
