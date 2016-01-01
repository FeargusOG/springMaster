package org.feargus.springmaster.invites.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AcceptInviteCtrlr {

    @RequestMapping("/acceptInvite")
    public String index(@RequestParam(value = "inviteToken", required = true) String token, Model model) {
	model.addAttribute("inviteToken", token);

	return "acceptInvite";
    }
}
