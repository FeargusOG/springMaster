package org.feargus.springmaster.security.auth;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 2102300255260556012L;
    private String authority;

    public CustomGrantedAuthority(String authorityIn) {
	/* Spring Security is looking for ROLE_USER, ROLE_ADMIN etc */
	this.authority = ROLE_PREFIX + authorityIn;
    }

    @Override
    public String getAuthority() {
	return this.authority;
    }
}
