package org.feargus.springmaster.invites.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateInviteCtrlr {
	private static final Logger log = LoggerFactory.getLogger(GenerateInviteCtrlr.class);
	
	@RequestMapping(value="/generateInvite")
	public String generateInvite(@RequestParam(value="userEmail", required=true) String userEmail, Model model){
		log.info("\n\nGONNA GENERATE HERE!!\n\n");
		
		model.addAttribute("userEmail", userEmail);
		
		return "generateInvite";
	}

}
