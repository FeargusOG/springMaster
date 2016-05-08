package org.feargus.springmaster.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeCtrlr {

    @RequestMapping("/home")
    public String home() {
	return "home";
    }

}
