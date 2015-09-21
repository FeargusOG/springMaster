package org.feargus.springmaster.controllers;

import org.feargus.springmaster.Application;
import org.feargus.springmaster.mail.Mailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestInviteCtrlr {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@RequestMapping("/requestInvite")
	public String requestInvite(){
		String token = "blaher1";
    	Mailer mailSender = new Mailer();
    	mailSender.sendMail("feargusogorman@gmail.com", "Invite Request", "I would like to join please! :) Token = "+token);
    	
    	log.info("Finished sending a mail....");
		
		return "requestInvite";
	}
}
