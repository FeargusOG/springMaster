package org.feargus.springmaster.invites.controllers;

import org.feargus.springmaster.uniqueids.UniqueTokenGenerator;
import org.feargus.springmaster.view.PostgresqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateInviteCtrlr {
	private static final Logger log = LoggerFactory.getLogger(GenerateInviteCtrlr.class);
	
	@RequestMapping(value="/generateInvite")
	public String generateInvite(@RequestParam(value="userEmail", required=true) String userEmail, Model model){
		UniqueTokenGenerator invTokGenerator = new UniqueTokenGenerator();
		String uniqueToken = invTokGenerator.getUniqueToken();
		
		log.info("\n\nGONNA GENERATE HERE!! "+uniqueToken+"\n\n");
		
		PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    	
    	jdbcTemplate.update("INSERT INTO invitetokens(token, useremail) VALUES (?,?)", uniqueToken, userEmail);
		
		
		model.addAttribute("userEmail", userEmail);
		
		return "generateInvite";
	}

}
