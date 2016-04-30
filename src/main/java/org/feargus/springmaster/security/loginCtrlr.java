package org.feargus.springmaster.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginCtrlr {
    @RequestMapping("/login")
    public String home() {
	return "login";
    }

    /*
     * @RequestMapping(value = "/login", method = RequestMethod.GET) public
     * String loginFormGet(Model model) { return "login"; }
     * 
     * @RequestMapping(value = "/login", method = RequestMethod.POST) public
     * String loginFormPost(Model model) {
     * 
     * }
     */
}
