package org.feargus.springmaster.users.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    /**
     * default generated serial
     */
    private static final long serialVersionUID = -8200771636764034343L;
    private Collection<? extends GrantedAuthority> userAuthorities;
    private String userPassword;
    private String userEmail;
    private boolean userActive;

    public void setUserAuthorities(Collection<? extends GrantedAuthority> userAuthorities) {
	this.userAuthorities = userAuthorities;
    }

    public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
    }

    public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
    }

    public void setUserActive(boolean userActive) {
	this.userActive = userActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return this.userAuthorities;
    }

    @Override
    public String getPassword() {
	return this.userPassword;
    }

    @Override
    public String getUsername() {
	return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true; // true is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
	return true; // true is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true; // true is not expired
    }

    @Override
    public boolean isEnabled() {
	return this.userActive;
    }

}
