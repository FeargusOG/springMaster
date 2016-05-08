package org.feargus.springmaster.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    /**
     * 
     */
    private static final long serialVersionUID = 2102300255260556012L;
    private static final Logger log = LoggerFactory.getLogger(CustomGrantedAuthority.class);
    private String authority;

    public CustomGrantedAuthority(String authorityIn) {
	this.authority = authorityIn;
    }

    @Override
    public String getAuthority() {
	log.info("\n\n\tWe are returning the auth here: " + this.authority);
	return this.authority;
    }

}
