package org.feargus.springmaster.security.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.feargus.springmaster.users.CustomUserDetails;
import org.feargus.springmaster.users.UserAccUtils;
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
	log.info("\n\n\tLoading user for email: " + lowerCaseEmail);

	CustomGrantedAuthority grantedAuth = new CustomGrantedAuthority("USER");
	Collection<CustomGrantedAuthority> authList = new ArrayList<CustomGrantedAuthority>();
	authList.add(grantedAuth);

	customUserDetails.setUserAuthorities(authList);
	customUserDetails.setUsername(lowerCaseEmail);
	customUserDetails.setUserActive(this.userUtils.selectIsActiveFromDB(lowerCaseEmail));
	customUserDetails.setPassword(this.userUtils.selectPasswordFromDB(lowerCaseEmail));

	log.info("\n\n\tHere is the password: " + customUserDetails.getPassword());
	log.info("\n\n\tHere is the active: " + customUserDetails.isEnabled());

	return customUserDetails;
    }

}
