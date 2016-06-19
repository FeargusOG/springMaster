package org.feargus.springmaster.security.auth;

import org.feargus.springmaster.users.CustomUserDetails;
import org.feargus.springmaster.users.UserAccUtils;
import org.feargus.springmaster.utils.UtilVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private UserAccUtils userUtils;

    public CustomUserDetailsService() {
	this.userUtils = new UserAccUtils();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
	CustomUserDetails customUserDetails = new CustomUserDetails();
	String lowerCaseEmail = userEmail.toLowerCase();

	log.info("Loading user for email: " + UtilVars.PII_START + lowerCaseEmail + UtilVars.PII_END);
	customUserDetails = this.userUtils.loadUserFromDb(lowerCaseEmail);

	return customUserDetails;
    }

}
