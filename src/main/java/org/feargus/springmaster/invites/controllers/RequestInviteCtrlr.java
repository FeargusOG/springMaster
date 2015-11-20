package org.feargus.springmaster.invites.controllers;

import org.feargus.springmaster.invites.models.InviteRequestObj;
import org.feargus.springmaster.mail.Mailer;
import org.feargus.springmaster.utils.SystemVars;
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
	
	private static final Logger log = LoggerFactory.getLogger(RequestInviteCtrlr.class);
	
	@RequestMapping(value="/requestInvite", method=RequestMethod.GET)
    public String greetingForm(@RequestParam(value="projectName", required=true) String requestedProj, Model model) {
		model.addAttribute("requestInvite", new InviteRequestObj());
		model.addAttribute("requestedProj", requestedProj);
        return "requestInvite";
    }

	@RequestMapping(value="/requestInvite", method=RequestMethod.POST)
	public String requestInvite(@RequestParam(value="projectName", required=true) String projectName, @ModelAttribute InviteRequestObj invite, Model model){
		String generatedEmailLink = SystemVars.rootUrl+"/generateInvite?userEmail="+invite.getUserEmail();
		
		log.info("Invite requested by: "+invite.getUserEmail());
		
    	Mailer mailSender = new Mailer();
    	mailSender.sendMail("feargusorg@gmail.com", "Invite Request for"+invite.getProjectName(), "I would like to join please! :)\n\nUser: "+invite.getUserEmail()+"\nProject: "+invite.getProjectName()+"\nInvite Generator URL: "+generatedEmailLink);
    	
    	log.info("Finished sending a mail requesting invite for: "+invite.getUserEmail());
		
		return "inviteGenerated";
	}
}
